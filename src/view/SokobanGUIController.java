package view;

import controller.displayer.Displayer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class SokobanGUIController extends Observable implements View, Initializable {

    @FXML
    GUIDisplayer displayer;

    public void setDisplayer(GUIDisplayer displayer) {
        this.displayer = displayer;
    }

    public SokobanGUIController() {
    }

    public void openFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose a level file");
        fc.setInitialDirectory(new File("./resources/levels"));
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML", "*.xml"),
                new FileChooser.ExtensionFilter("Text", "*.txt"),
                new FileChooser.ExtensionFilter("Obj", "*.obj")
        );
        File chosen = fc.showOpenDialog(null);
        if (chosen != null) {
            setChanged();
            notifyObservers("load " + chosen.getAbsoluteFile());
        }
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
}
