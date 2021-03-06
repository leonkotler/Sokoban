package boot;

import controller.SokobanController;
import controller.server.Cli;
import controller.server.MyServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.SokobanModel;
import view.SokobanGUIController;

import java.io.IOException;

public class Run extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/SokobanBoard.fxml"));
        Parent root = fxmlLoader.load();

        SokobanGUIController view = fxmlLoader.getController();
        SokobanModel model = new SokobanModel();
        SokobanController controller = new SokobanController(model, view);

        view.addObserver(controller);
        model.addObserver(controller);
        controller.start();

        primaryStage.setTitle("Sokoban");
        primaryStage.setScene(new Scene(root, 700, 600));
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> view.exit());
    }


    public static void main(String[] args) {
        try {
            // start in server mode
            if (args.length == 2) {
                if (args[0].equals("-server")) {

                    SokobanModel model = new SokobanModel();
                    SokobanController controller = new SokobanController(model, new MyServer(Integer.parseInt(args[1]), new Cli()));

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
