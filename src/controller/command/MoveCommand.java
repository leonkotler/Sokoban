package controller.command;


import model.Model;
import utils.Direction;

import java.io.IOException;
import java.util.LinkedList;

public class MoveCommand implements Command {

    protected Model model;
    protected Direction direction = null;

    public MoveCommand() {
    }

    public MoveCommand(Model model, String direction) throws IOException {
        setModel(model);
        setDirection(direction);
    }

    public MoveCommand(Model model) throws IOException {
        setModel(model);
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) throws IOException {
        if (model == null)
            throw new IOException("Please provide a valid model");
        this.model = model;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(String direction) throws IOException {
        if (direction == null | !isVaildDirection(direction))
            throw new IOException("Please provide a valid direction");

        this.direction = Direction.valueOf(direction.toUpperCase());
    }

    @Override
    public void execute() throws IOException {
        if (model.getCurrentLvl() == null)
            throw new IOException("No level found, try loading one first");

        model.move(direction);
    }

    @Override
    public void setParams(LinkedList<String> params) throws IOException {
        setDirection(params.getFirst());
    }

    public boolean isVaildDirection(String direction) {
        // checks if the provided string is a valid direction (enum)
        for (Direction dir : Direction.values())
            if (dir.name().equals(direction.toUpperCase()))
                return true;

        return false;
    }
}
