package model.data.level_item;

import model.data.level.Location;

import java.io.Serializable;

/* The TargetTile represents that target on which the box should be put on */
public class TargetTile extends Tile implements Serializable {

    public TargetTile(Location location) {
        super(location);
    }

    public TargetTile() {
    }

    @Override
    public String toString() {
        // if the target contains any other item, then it gets displayed, otherwise - displayer "o"
        if (contains==null)
            return "o";
        else
            return contains.toString();
    }
}
