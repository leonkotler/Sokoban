package view;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.hibernate.HibernateUtil;
import utils.hibernate.Score;

import java.net.URL;
import java.sql.Time;
import java.util.Iterator;
import java.util.ResourceBundle;

public class PlayerScoreBoardController implements Initializable {

    @FXML
    protected TableView<Score> playerTable;
    @FXML
    protected TableColumn<Score, String> nameColumn;
    @FXML
    protected TableColumn<Score, String> levelColumn;
    @FXML
    protected TableColumn<Score, Integer> stepsColumn;
    @FXML
    protected TableColumn<Score, Time> timeColumn;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerScoreBoardController() {
    }

    public PlayerScoreBoardController(String name) {
        this.name = name;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initTableColumns();

        initSearch();
    }

    public void initSearch() {
        ObservableList<Score> data = FXCollections.observableArrayList();

        Session session = HibernateUtil.getSessionFactory().openSession();

        if (!session.getTransaction().isActive())
            session.beginTransaction();

        Query q = session.createQuery("from Score s where s.id.playerName=:playerName");
        q.setParameter("playerName",name);

        Iterator ite = q.iterate();

        while (ite.hasNext()) {
            Score score = (Score) ite.next();
            data.add(score);
        }

        playerTable.getItems().clear();
        playerTable.setItems(data);
        // sort by steps on default
        playerTable.getSortOrder().setAll(stepsColumn);
        session.close();

    }

    protected void initTableColumns(){
        // binding the columns to the model object
        nameColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getId().getPlayerName()));
        levelColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getId().getLevelName()));
        stepsColumn.setCellValueFactory(new PropertyValueFactory<>("steps"));
        // get rid of the auto +2 hour GMT addition (7200000 == 2 hours)
        timeColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(new Time(param.getValue().getTime()-7200000)));
    }
}
