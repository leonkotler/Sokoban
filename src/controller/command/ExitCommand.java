package controller.command;

import controller.Controller;

import java.io.IOException;
import java.util.LinkedList;

public class ExitCommand implements Command {

    Controller controller=null;

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) throws IOException {
        if (controller==null)
            throw new IOException("Please provide a valid controller");
        this.controller = controller;
    }

    public ExitCommand(Controller controller) throws IOException {
        setController(controller);
    }

    @Override
    public void execute() throws IOException{
        controller.stop();
    }

    @Override
    public void setParams(LinkedList<String> params) throws IOException {
        if (params.size()!=0)
            throw new IOException("Usage: exit");
    }
}
