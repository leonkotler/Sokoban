package controller.server;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Scanner;

public class Cli extends Observable implements ClientHandler {

    public OutputStream outToClient;
    protected String exitStr = "exit";

    public OutputStream getOutToClient() {
        return outToClient;
    }

    @Override
    public void handleClient(InputStream inFromClient, OutputStream outToClient) {

        this.outToClient = outToClient;
        String userInput;

        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(inFromClient)));
        PrintWriter writer = new PrintWriter(outToClient);
        writer.println("=========================================");
        writer.println("                Welcome to               ");
        writer.println(" _____       _         _                 ");
        writer.println("/  ___|     | |       | |                ");
        writer.println("\\ `--.  ___ | | _____ | |__   __ _ _ __  ");
        writer.println(" `--. \\/ _ \\| |/ / _ \\| '_ \\ / _` | '_ \\ ");
        writer.println("/\\__/ / (_) |   < (_) | |_) | (_| | | | |");
        writer.println("\\____/ \\___/|_|\\_\\___/|_.__/ \\__,_|_| |_|");
        writer.println();
        writer.println("=========================================");
        writer.flush();
        help(outToClient);

        try {
            while (!scanner.hasNext(exitStr)) {
                // splitting user input on spaces
                userInput = scanner.nextLine();
                // sending the user's input to the controller
                setChanged();
                notifyObservers(userInput);
            }
            // disconnecting the client
            writer.println("bye");
            writer.flush();

        } catch (NoSuchElementException e){
            // catching sudden disconnection
            System.out.println("Connection closed");
        }
    }

    public void help(OutputStream outToClient) {
        // displays the help menu
        PrintWriter writer = new PrintWriter(outToClient);
        writer.println();
        writer.println("Menu:");
        writer.println("    Type \"load fullpath\" to load a level from a file");
        writer.println("    Type \"save fullpath\" to save a level from to file");
        writer.println("    Type \"move direction\" (up,down,left,right) to move the character in the desired direction");
        writer.println("    Type \"exit\" to exit the game");
        writer.println("    Type \"help\" show the help menu");
        writer.println();
        writer.flush();
    }

}

