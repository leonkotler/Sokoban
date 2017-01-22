package view;

import controller.displayer.Displayer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class SokobanGUIController extends Observable implements View, Initializable {

    @FXML
    GUIDisplayer displayer;
    @FXML
    Label steps;

    private Media media = new Media(new File("C:\\Users\\leonk\\Documents\\JavaCourse\\Sokoban_GUI\\resources\\music\\zizibum.mp3").toURI().toString());
    private MediaPlayer mediaPlayer = new MediaPlayer(media);

    public void setDisplayer(GUIDisplayer displayer) {
        this.displayer = displayer;
    }

    public SokobanGUIController() {
        mediaPlayer.setVolume(0.2);
        mediaPlayer.setAutoPlay(true);
    }

    public void openFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose a level file");
        fc.setInitialDirectory(new File("./resources/levels"));
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text", "*.txt"),
                new FileChooser.ExtensionFilter("XML", "*.xml"),
                new FileChooser.ExtensionFilter("Obj", "*.obj")
        );

        File chosen = fc.showOpenDialog(null);
        if (chosen != null) {
            setChanged();
            notifyObservers("load " + chosen.getAbsoluteFile());
        }
        displayer.requestFocus();
    }

    public void saveFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save level");
        fc.setInitialDirectory(new File("./resources/saved_levels"));
        File chosen = fc.showSaveDialog(null);

        if (chosen != null) {
            setChanged();
            notifyObservers("save " + chosen.getAbsoluteFile());
        }
    }

    public void exit() {
        Platform.exit();
        setChanged();
        notifyObservers("exit");
    }

    @Override
    public void display() {
        setChanged();
        notifyObservers("display");
    }

    @Override
    public Displayer getDisplayer() {
        return displayer;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayer.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->displayer.requestFocus());

        displayer.setOnKeyPressed(event -> {
            if (event.getCode()== KeyCode.UP){
                setChanged();
                notifyObservers("move up");
            }
            if (event.getCode()== KeyCode.DOWN){
                setChanged();
                notifyObservers("move down");
            }
            if (event.getCode()== KeyCode.LEFT){
                setChanged();
                notifyObservers("move left");
            }
            if (event.getCode()== KeyCode.RIGHT){
                setChanged();
                notifyObservers("move right");
            }
        });
    }

    @Override
    public void passException(Exception e) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Attention");
                alert.setContentText(e.getMessage());

                alert.showAndWait();
            }
        });

    }

    @Override
    public void passPassmessage(String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Victory!");
                alert.setHeaderText("Hurray!");
                alert.setContentText(message);

                alert.showAndWait();
            }
        });

    }

    public void playPause(){
        if (mediaPlayer.getStatus()== MediaPlayer.Status.PLAYING)
            mediaPlayer.pause();
        else mediaPlayer.play();
    }
}
