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
    private StringProperty wallImg;
    private StringProperty boxImg;
    private StringProperty tileImg;
    private StringProperty targetImg;
    private StringProperty playerImg;


    public GUIDisplayer() {
        wallImg=new SimpleStringProperty();
        boxImg=new SimpleStringProperty();
        tileImg=new SimpleStringProperty();
        targetImg=new SimpleStringProperty();
        playerImg=new SimpleStringProperty();
    }

    @Override
    public void display() throws IOException {
        if (model.getCurrentLvl() != null) {
            double W = getWidth();
            double H = getHeight();

            double w = W / model.getCurrentLvl().getxLenght();
            double h = H / model.getCurrentLvl().getyWidth();

            Image wall = new Image(new FileInputStream(wallImg.get()));
            Image box = new Image(new FileInputStream(boxImg.get()));
            Image target = new Image(new FileInputStream(targetImg.get()));
            Image player = new Image(new FileInputStream(playerImg.get()));
            Image tile = new Image(new FileInputStream(tileImg.get()));

            GraphicsContext gc = getGraphicsContext2D();
            gc.clearRect(0, 0, W, H);

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

    public String getWallImg() {
        return wallImg.get();
    }

    public StringProperty wallImgProperty() {
        return wallImg;
    }

    public void setWallImg(String wallImg) {
        this.wallImg.set(wallImg);
    }

    public String getBoxImg() {
        return boxImg.get();
    }

    public StringProperty boxImgProperty() {
        return boxImg;
    }

    public void setBoxImg(String boxImg) {
        this.boxImg.set(boxImg);
    }

    public String getTileImg() {
        return tileImg.get();
    }

    public StringProperty tileImgProperty() {
        return tileImg;
    }

    public void setTileImg(String tileImg) {
        this.tileImg.set(tileImg);
    }

    public String getPlayerImg() {
        return playerImg.get();
    }

    public StringProperty playerImgProperty() {
        return playerImg;
    }

    public void setPlayerImg(String playerImg) {
        this.playerImg.set(playerImg);
    }

    public String getTargetImg() {
        return targetImg.get();
    }

    public StringProperty targetImgProperty() {
        return targetImg;
    }

    public void setTargetImg(String targetImg) {
        this.targetImg.set(targetImg);
    }
}
