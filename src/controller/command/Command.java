package controller.command;


import java.io.IOException;
import java.util.LinkedList;

public interface Command {
    void execute() throws IOException;

    void setParams(LinkedList<String> params) throws IOException;
}
