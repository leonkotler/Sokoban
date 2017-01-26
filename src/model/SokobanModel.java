package model;

import model.data.level.Level;
import model.policy.MySokobanPolicy;
import model.policy.Policy;
import utils.Direction;

import java.io.IOException;
import java.util.Observable;


public class SokobanModel extends Observable implements Model {

    Level currentLevel;
    Policy policy;

    @Override
    public Level getCurrentLvl() {
        return currentLevel;
    }

    @Override
    public void setCurrentLvl(Level level) {
        this.currentLevel = level;
        try {
            this.policy = new MySokobanPolicy(level);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setChanged();
        notifyObservers("display");
    }

    public void move(Direction direction) {
        if (!currentLevel.isWon()) {
            policy.checkPolicy(direction);
            setChanged();
            notifyObservers("display");

            if (currentLevel.isWon()) {
                setChanged();
                notifyObservers("win " + currentLevel.getStepCounter());
            }
        }

    }

    @Override
    public Policy getPolicy() {
        return policy;
    }

    @Override
    public void setPolicy(Policy policy) {
        this.policy = policy;
    }
}
