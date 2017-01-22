package controller;

import controller.command.*;
import controller.displayer.CLIDisplayer;
import controller.server.MyServer;
import model.Model;
import view.View;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class MyController implements Controller {

    protected Model model;
    protected View view;
    protected BlockingQueue<Command> queue = new ArrayBlockingQueue<Command>(1024);
    protected HashMap<String, Command> commands;
    protected MyServer server = null;
    boolean run = true;

    public void setServer(MyServer server) {
        this.server = server;
    }

    public MyServer getServer() {
        return server;
    }

    public MyController(Model model, MyServer server) throws IOException {
        this.model = model;

        if (server != null) this.server = server;
        else throw new NullPointerException("Server cannot be null");

        // starting the server
        server.start();

        // initializing the commands
        commands = new HashMap<>();
        commands.put("load", new LoadCommand(this.model));
        commands.put("save", new SaveCommand(this.model));
        commands.put("move", new MoveCommand(this.model));
        commands.put("exit", new ExitCommand(this));
        commands.put("win",new WinCommand(this.server));
        commands.put("display", new DisplayCommand(this.model,new CLIDisplayer(this.server)));
    }

    public MyController(Model model, View view) throws IOException {
        this.model = model;
        this.view = view;
        // initializing the commands
        commands = new HashMap<>();
        commands.put("load", new LoadCommand(this.model));
        commands.put("save", new SaveCommand(this.model));
        commands.put("move", new MoveCommand(this.model));
        commands.put("exit", new ExitCommand(this));
        commands.put("win",new WinCommand(this.view));
        commands.put("display", new DisplayCommand(this.model,view.getDisplayer()));
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            LinkedList<String> params = resolveCommand((String) arg);
            String commandKey = params.removeFirst();
            Command command;
            command = commands.get(commandKey);

            // validating command
            if (command == null) {
                if (server != null)
                    // send exception to the client
                    sendException(server.getClientHandler().getOutToClient(), new IOException("Unknown command, try again"));
                else
                    // send exception to the console
                    sendException(System.out, new IOException("Unknown command, try again"));
            } else {
                // command is valid, send to queue for execution
                command.setParams(params);
                this.insertCommand(command);
            }

        } catch (IOException | InterruptedException | NullPointerException | ClassCastException e) {
            if (server != null)
                // send exception to the client
                sendException(server.getClientHandler().getOutToClient(), e);
            else {
                // send exception to the console
                sendException(System.out, e);
                // send exception to the gui
                view.passException(e);
            }
        }
    }

    @Override
    public void insertCommand(Command command) throws InterruptedException {
        // putting the command in the blocking queue
        queue.put(command);
    }

    @Override
    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (run) {
                    try {
                        queue.take().execute();
                    } catch (IOException | InterruptedException e) {
                        if (server != null)
                            sendException(server.getClientHandler().getOutToClient(), e);
                        else {
                            System.out.println(e.getMessage());
                            view.passException(e);
                        }
                    }
                }
                if (server != null) server.stop();
            }
        }).start();
    }

    @Override
    public void stop() {
        run = false;
    }

    protected void sendException(OutputStream out, Exception e) {
        // sending the exception to the given output stream
        PrintWriter errorWriter = new PrintWriter(out);
        errorWriter.println(e.getMessage());
        errorWriter.flush();
    }

    protected LinkedList<String> resolveCommand(String command) throws IOException {
        if (command == null) throw new IOException("Please provide a command");
        // resolving the command string
        String[] commandArray;
        LinkedList<String> commandList = new LinkedList<>();

        if (command.contains(" ")) {
            commandArray = command.split(" ");
            for (String s : commandArray)
                commandList.add(s);
        } else
            commandList.add(command);

        return commandList;
    }

}

