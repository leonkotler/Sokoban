package controller.command;


import controller.server.MyServer;
import view.View;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

public class WinCommand implements Command {

    MyServer server;
    View view;
    String steps;
    String timePassed;

    public WinCommand(MyServer server) {
        this.server = server;
    }

    public WinCommand(View view) {
        this.view = view;
    }

    @Override
    public void execute() throws IOException {
        if (server != null) {
            PrintWriter errorWriter = new PrintWriter(server.getClientHandler().getOutToClient());
            errorWriter.println("Congratulations! You've won!\nIt took you " + steps + " steps and " + timePassed + "\nWould you like to enter you results to the score board?");
            errorWriter.flush();
        } else {
            view.winMessage("Congratulations! You've won!\nIt took you " + steps + " steps and " + timePassed + "\nWould you like to enter you results to the score board?");
        }
    }

    @Override
    public void setParams(LinkedList<String> params) throws IOException {
        steps = params.removeFirst();
        timePassed = params.removeFirst();
    }
}
