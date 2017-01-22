package boot;

import controller.MyController;
import controller.server.Cli;
import controller.server.MyServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.MyModel;
import view.SokobanGUIController;

import java.io.File;
import java.io.IOException;

public class Run extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/SokobanBoard.fxml"));
        Parent root = fxmlLoader.load();

        SokobanGUIController view = fxmlLoader.getController();
        MyModel model = new MyModel();
        MyController controller = new MyController(model, view);

        view.addObserver(controller);
        model.addObserver(controller);
        controller.start();

        primaryStage.setTitle("Sokoban");
        primaryStage.setScene(new Scene(root, 613, 462));
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> view.exit());
    }


    public static void main(String[] args) {
        try {
            // start in server mode
            if (args.length == 2) {
                if (args[0].equals("-server")) {

                    MyModel model = new MyModel();
                    MyController controller = new MyController(model, new MyServer(Integer.parseInt(args[1]), new Cli()));

                    model.addObserver(controller);
                    ((Cli) (controller.getServer().getClientHandler())).addObserver(controller);
                    controller.start();
                }
            }

            // start in GUI mode
            else if (args.length == 0) launch(args);

            else
                System.out.println("Run the programs without parameters for GUI mode or provide -server <PORT> for server mode");


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
