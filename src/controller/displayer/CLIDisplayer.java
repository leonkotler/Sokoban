package controller.displayer;

import controller.server.MyServer;
import model.Model;
import model.data.level_item.Tile;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CLIDisplayer implements Displayer {

    protected MyServer server;
    protected Model model;

    public CLIDisplayer(MyServer server) throws IOException {
        this.server = server;
    }

    @Override
    public void display() throws IOException {
        if (model.getCurrentLvl() == null)
            throw new IOException("No level to display, try loading one first");

        // extracting the output stream from the server
        PrintWriter out = new PrintWriter(this.server.getClientHandler().getOutToClient());

        for (ArrayList<Tile> list : model.getCurrentLvl().getLevelMap()) {
            for (Tile tile : list) {
                out.print(tile.toString());
            }
            out.println();
            out.flush();
        }
    }

    @Override
    public void setModel(Model model) {
        this.model=model;
    }
}
