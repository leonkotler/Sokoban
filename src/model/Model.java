package model;


import model.data.level.Level;
import model.policy.Policy;
import utils.Direction;

public interface Model {
    Level getCurrentLvl();
    void setCurrentLvl(Level level);
    Policy getPolicy();
    void setPolicy(Policy policy);
    void move(Direction direction);
}
