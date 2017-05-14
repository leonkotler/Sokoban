package view;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.hibernate.HibernateUtil;
import utils.hibernate.Score;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.Date;
import java.util.Iterator;
import java.util.ResourceBundle;


public class MainScoreBoardController implements Initializable {

    public MainScoreBoardController() {
    }

    String levelName;

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    @FXML
    protected TextField levelNameTextBox;
    @FXML
    protected TextField playerNameTextBox;
    @FXML
    protected TableView<Score> mainTable;
    @FXML
    protected TableColumn<Score, String> nameColumn;
    @FXML
    protected TableColumn<Score, String> levelColumn;
    @FXML
    protected TableColumn<Score, Integer> stepsColumn;
    @FXML
    protected TableColumn<Score, Time> timeColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initTableColumns();

        initSearch();

        mainTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2 && mainTable.getSelectionModel().getSelectedItem() != null) {

                    // get the current cell's selected player name
                    String selectedPlayer = mainTable.getSelectionModel().getSelectedItem().getId().getPlayerName();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/PlayerScoreBoard.fxml"));

                    try {
                        Parent root2 = fxmlLoader.load();

                        // pass the name to the new controller and search for it
                        PlayerScoreBoardController controller = fxmlLoader.getController();
                        controller.setName(selectedPlayer);
                        controller.initSearch();

                        // setup window details
                        Stage stage = new Stage();
                        stage.setTitle("Scoreboard for player \"" + selectedPlayer + "\"");
                        stage.setScene(new Scene(root2));
                        stage.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void searchByPlayerName() {

        ObservableList<Score> data = FXCollections.observableArrayList();

        Session session = HibernateUtil.getSessionFactory().openSession();

        if (!session.getTransaction().isActive())
            session.beginTransaction();

        Query q = session.createQuery("from Score s where s.id.playerName=:playerName");
        q.setParameter("playerName", playerNameTextBox.getText());

        Iterator ite = q.iterate();

        while (ite.hasNext()) {
            Score score = (Score) ite.next();
            data.add(score);
        }
        mainTable.getItems().clear();
        mainTable.setItems(data);
        // sort by steps on default
        mainTable.getSortOrder().setAll(stepsColumn);
        session.close();
    }

    public void searchByLevelName() {

        ObservableList<Score> data = FXCollections.observableArrayList();

        Session session = HibernateUtil.getSessionFactory().openSession();

        if (!session.getTransaction().isActive())
            session.beginTransaction();

        Query q = session.createQuery("from Score s where s.id.levelName=:levelName");
        q.setParameter("levelName", levelNameTextBox.getText());

        Iterator ite = q.iterate();

        while (ite.hasNext()) {
            Score score = (Score) ite.next();
            data.add(score);
        }
        mainTable.getItems().clear();
        mainTable.setItems(data);
        // sort by steps on default
        mainTable.getSortOrder().setAll(stepsColumn);
        session.close();
    }

    protected void initTableColumns() {
        // binding the columns to the model object
        nameColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getId().getPlayerName()));
        levelColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getId().getLevelName()));
        stepsColumn.setCellValueFactory(new PropertyValueFactory<>("steps"));
        // get rid of the auto +2 hour GMT addition (7200000 == 2 hours)
        timeColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(new Time(param.getValue().getTime() - 7200000)));
    }

    public void initSearch() {
        ObservableList<Score> data = FXCollections.observableArrayList();

        Session session = HibernateUtil.getSessionFactory().openSession();

        if (!session.getTransaction().isActive())
            session.beginTransaction();

        Query q = session.createQuery("from Score s where s.id.levelName=:levelName");
        q.setParameter("levelName", levelName);

        Iterator ite = q.iterate();

        while (ite.hasNext()) {
            Score score = (Score) ite.next();
            data.add(score);
        }

        mainTable.getItems().clear();
        mainTable.setItems(data);

        // sort by steps on default
        mainTable.getSortOrder().setAll(stepsColumn);
        session.close();
    }
}
