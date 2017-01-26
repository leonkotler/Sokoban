package view;

import controller.displayer.Displayer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.Model;
import model.data.level_item.Tile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;


public class GUIDisplayer extends Canvas implements Displayer {

    protected Model model;

    public GUIDisplayer() {

    }

    @Override
    public void display() throws IOException {
        if (model.getCurrentLvl() != null) {

            double W = getWidth();
            double H = getHeight();

            double w = W / model.getCurrentLvl().getX();
            double h = H / model.getCurrentLvl().getY();


            Image wall = new Image(getClass().getResourceAsStream("/images/wall.jpg"));
            Image box = new Image(getClass().getResourceAsStream("/images/crate.jpg"));
            Image target = new Image(getClass().getResourceAsStream("/images/target.jpg"));
            Image player = new Image(getClass().getResourceAsStream("/images/player.jpg"));
            Image tile = new Image(getClass().getResourceAsStream("/images/tile.jpg"));

            GraphicsContext gc = getGraphicsContext2D();
            gc.clearRect(0, 0, W, H);

            for (int i = 0; i < model.getCurrentLvl().getX(); i++)
                for (int j = 0; j < model.getCurrentLvl().getY(); j++) {
                    gc.drawImage(tile, j * w, i * h, w, h);
                }


            for (ArrayList<Tile> list : model.getCurrentLvl().getLevelMap()) {
                for (Tile t : list) {
                    if (t.toString().equals("@"))
                        gc.drawImage(box, t.getLocation().getY() * w, t.getLocation().getX() * h, w, h);
                    else if (t.toString().equals("A"))
                        gc.drawImage(player, t.getLocation().getY() * w, t.getLocation().getX() * h, w, h);
                    else if (t.toString().equals("o"))
                        gc.drawImage(target, t.getLocation().getY() * w, t.getLocation().getX() * h, w, h);
                    else if (t.toString().equals("#"))
                        gc.drawImage(wall, t.getLocation().getY() * w, t.getLocation().getX() * h, w, h);
                    else gc.drawImage(tile, t.getLocation().getY() * w, t.getLocation().getX() * h, w, h);
                }
            }
        }
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

}
