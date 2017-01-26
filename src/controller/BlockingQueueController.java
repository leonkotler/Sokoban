package controller;


import controller.command.Command;
import controller.server.MyServer;
import model.Model;
import view.View;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/* A general command executing blocking queue controller */
public abstract class BlockingQueueController implements Controller {

    protected BlockingQueue<Command> queue = new ArrayBlockingQueue<Command>(100);
    protected Model model = null;
    protected View view = null;
    protected MyServer server = null;
    protected boolean run = true;

    public BlockingQueueController(Model model, View view) throws IOException {

        // constructor that receives a view, for a local GUI driven session
        if (model != null) this.model = model;
        else throw new NullPointerException("Model cannot be null");

        if (view != null) this.view = view;
        else throw new NullPointerException("View cannot be null");
    }

    public BlockingQueueController(Model model, MyServer server) throws IOException {
        // constructor that receives a server for handling remote connections

        if (model != null) this.model = model;
        else throw new NullPointerException("Model cannot be null");

        if (server != null) this.server = server;
        else throw new NullPointerException("Server cannot be null");
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public MyServer getServer() {
        return server;
    }

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
}
