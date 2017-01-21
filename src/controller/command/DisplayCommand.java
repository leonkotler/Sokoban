package controller.command;

import controller.displayer.Displayer;
import model.Model;

import java.io.IOException;
import java.util.LinkedList;


public class DisplayCommand implements Command {

    protected Displayer displayer = null;
    protected Model model;

    public DisplayCommand(Model model, Displayer displayer) throws IOException {
        this.displayer = displayer;
        this.model = model;
        displayer.setModel(model);
    }


    @Override
    public void execute() throws IOException {
        displayer.display();
    }

    @Override
    public void setParams(LinkedList<String> params) throws IOException {
        if (params.size() != 0)
            throw new IOException("Usage: display");
    }
}
