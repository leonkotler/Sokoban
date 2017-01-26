package controller;

import controller.BlockingQueueController;
import controller.command.*;
import controller.displayer.CLIDisplayer;
import controller.server.MyServer;
import model.Model;
import view.View;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;


public class SokobanController extends BlockingQueueController {


    private HashMap<String, Command> commands = null;


    public SokobanController(Model model, MyServer server) throws IOException {
        // constructor that receives a server for handling remote connections

        // initiate controller
        super(model,server);

        // starting the server
        server.start();

        // initializing the commands
        initCommands();
    }

    public SokobanController(Model model, View view) throws IOException {
        // constructor that receives a view, for a local GUI driven session

        // initiate controller
        super(model,view);

        // initializing the commands
        initCommands();
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
                    // send exception to the gui
                    view.passException(new IOException("Unknown command, try again"));
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

    private LinkedList<String> resolveCommand(String command) throws IOException {
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

    private void initCommands() throws IOException {
        commands = new HashMap<>();
        commands.put("load", new LoadCommand(this.model));
        commands.put("save", new SaveCommand(this.model));
        commands.put("move", new MoveCommand(this.model));
        commands.put("exit", new ExitCommand(this));
        commands.put("win", new WinCommand(this.view));

        if (this.server != null) {
            commands.put("display", new DisplayCommand(this.model, new CLIDisplayer(this.server)));
            commands.put("win", new WinCommand(this.server));
        }
        else {
            commands.put("display", new DisplayCommand(this.model, view.getDisplayer()));
            commands.put("win", new WinCommand(this.view));
        }

    }
}


