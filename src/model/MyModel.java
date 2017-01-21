package model;

import model.data.level.Level;
import model.policy.MySokobanPolicy;
import model.policy.Policy;
import utils.Direction;

import java.io.IOException;
import java.util.Observable;


public class MyModel extends Observable implements Model {

    Level currentLevel;
    Policy policy;

    @Override
    public Level getCurrentLvl() {
        return currentLevel;
    }

    @Override
    public void setCurrentLvl(Level level) {
        this.currentLevel=level;
        try {
            this.policy=new MySokobanPolicy(level);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setChanged();
        notifyObservers("display");
    }

    public void move(Direction direction){
        policy.checkPolicy(direction);
        if (currentLevel.isWon()){
            setChanged();
            notifyObservers("won");
        }
        setChanged();
        notifyObservers("display");
    }

    @Override
    public Policy getPolicy() {
        return policy;
    }

    @Override
    public void setPolicy(Policy policy) {
        this.policy=policy;
    }
}
