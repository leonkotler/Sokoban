package controller.command;

import model.Model;
import utils.hibernate.HibernateUtil;
import view.View;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.LinkedList;


public class SaveScoreCommand implements Command {
    String playerName;
    Model model;
    View view;

    public SaveScoreCommand(Model model, View view) {
        this.model=model;
        this.view=view;
    }

    @Override
    public void execute() throws IOException {
        long elapsedTime = model.getCurrentLvl().getStopWatch().getElapsedTimeInMillies();

        String levelName=model.getCurrentLvl().getLevelName();
        int steps = model.getCurrentLvl().getStepCounter();

        // saving the score
        if(!HibernateUtil.SaveScore(playerName,levelName,steps,elapsedTime)){
            view.passException(new IOException("Sorry, you haven't improved your result. Try again!"));
        }
    }

    @Override
    public void setParams(LinkedList<String> params) throws IOException {
        playerName=params.removeFirst();
    }
}
