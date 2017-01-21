package model.data.level_item;

import model.data.level.Location;

/* Item defines an Item that can be placed in a Level's map */
public interface Item {
    String getType();
    Location getLocation();
    void setLocation(Location location);
}
