package model.policy;

import model.data.level.Level;
import model.data.level.Location;
import model.data.level_item.Tile;
import utils.Direction;

import java.io.IOException;

/* describes the sokobn default policy */
public class MySokobanPolicy implements Policy {

    protected Level level=null;

    public MySokobanPolicy() {
    }

    public MySokobanPolicy(Level level) throws IOException {
        setLevel(level);
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) throws IOException {
        if (level==null)
            throw new IOException("Please provide a valid level");
        this.level = level;
    }

    @Override
    /* Checks if the command is legal and executes it */
    public boolean checkPolicy(Direction direction) {
        Location playerLocation = level.getPlayerLocation();

        // defining the adjacent cells in relation to the player
        Tile playerTileUp = initTile(Direction.UP, 1);
        Tile playerTileDown = initTile(Direction.DOWN, 1);
        Tile playerTileRight = initTile(Direction.RIGHT, 1);
        Tile playerTileLeft = initTile(Direction.LEFT, 1);

        // defining the adjacent cells in relation to the box and checking if the ranges are withing the bounds of the map
        Tile boxTileUp = initTile(Direction.UP, 2);
        Tile boxTileDown = initTile(Direction.DOWN, 2);
        Tile boxTileRight = initTile(Direction.RIGHT, 2);
        Tile boxTileLeft = initTile(Direction.LEFT, 2);

        switch (direction) {
            // deciding based on the direction
            case DOWN:
                if (playerTileDown.getContains() == null) {
                    level.moveItem(playerTileDown.getLocation(), playerLocation);
                    level.setPlayerLocation(playerTileDown.getLocation());
                    return true;
                }
                if (playerTileDown.getContains().getType().equals("WallItem"))
                    return false;
                if (playerTileDown.getContains().getType().equals("BoxItem"))
                    return moveBox(boxTileDown, playerTileDown);
                break;
            case UP:
                if (playerTileUp.getContains() == null) {
                    level.moveItem(playerTileUp.getLocation(), playerLocation);
                    level.setPlayerLocation(playerTileUp.getLocation());
                    return true;
                }
                if (playerTileUp.getContains().getType().equals("WallItem"))
                    return false;
                if (playerTileUp.getContains().getType().equals("BoxItem"))
                    return moveBox(boxTileUp, playerTileUp);

                break;
            case RIGHT:
                if (playerTileRight.getContains() == null) {
                    level.moveItem(playerTileRight.getLocation(), playerLocation);
                    level.setPlayerLocation(playerTileRight.getLocation());
                    return true;
                }
                if (playerTileRight.getContains().getType().equals("WallItem"))
                    return false;
                if (playerTileRight.getContains().getType().equals("BoxItem"))
                    return moveBox(boxTileRight, playerTileRight);

                break;
            case LEFT:
                if (playerTileLeft.getContains() == null) {
                    level.moveItem(playerTileLeft.getLocation(), playerLocation);
                    level.setPlayerLocation(playerTileLeft.getLocation());
                    return true;
                }
                if (playerTileLeft.getContains().getType().equals("WallItem"))
                    return false;
                if (playerTileLeft.getContains().getType().equals("BoxItem"))
                    return moveBox(boxTileLeft, playerTileLeft);

                break;
        }
        return false;
    }

    // checks conditions for moving the box
    private boolean moveBox(Tile newLocation, Tile currentLocation) {
        // if newLocation is null, then array was out of bounds
        if (newLocation == null)
            return false;
        else {
            // can move the box
            if (newLocation.getContains() == null) {
                level.moveItem(newLocation.getLocation(), currentLocation.getLocation()); // moving the box
                level.moveItem(currentLocation.getLocation(), level.getPlayerLocation()); // moving the player
                level.setPlayerLocation(currentLocation.getLocation());
                return true;
            }
            if (newLocation.getContains().getType().equals("WallItem"))
                // won't move the box if there's a wall in the adjacent cell
                return false;
            if (newLocation.getContains().getType().equals("BoxItem"))
                // won't move the box if there's another box in the adjacent cell
                return false;
        }
        return false;
    }

    private Tile initTile(Direction direction, int offset) {
        // returns the tile based on the given direction and offset, relative to the player's location
        switch (direction) {
            case UP:
                if (level.getPlayerLocation().getX() - offset < 0 || level.getPlayerLocation().getY() > level.getLevelMap().get(level.getPlayerLocation().getX() - offset).size()-1)
                    return null;
                else
                    return level.getLevelMap().get(level.getPlayerLocation().getX() - offset).get(level.getPlayerLocation().getY());
            case DOWN:
                if (level.getPlayerLocation().getX() + offset > level.getxLenght() - 1 || level.getPlayerLocation().getY() > level.getLevelMap().get(level.getPlayerLocation().getX() + offset).size()-1 )
                    return null;
                else
                    return level.getLevelMap().get(level.getPlayerLocation().getX() + offset).get(level.getPlayerLocation().getY());
            case RIGHT:
                if (level.getPlayerLocation().getY() + offset > level.getyWidth() - 1)
                    return null;
                else
                    return level.getLevelMap().get(level.getPlayerLocation().getX()).get(level.getPlayerLocation().getY() + offset);
            case LEFT:
                if (level.getPlayerLocation().getY() - offset < 0)
                    return null;
                else
                    return level.getLevelMap().get(level.getPlayerLocation().getX()).get(level.getPlayerLocation().getY() - offset);
        }
        return null;
    }
}
