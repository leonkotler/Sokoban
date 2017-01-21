package model.data.level;

import model.data.level_item.Tile;

import java.io.*;
import java.util.ArrayList;

/* MyObjectLevelSaver will save a level in a text format to a stream */
public class MyTextLevelSaver implements LevelSaver {

    @Override
    public void saveLevel(Level level, OutputStream stream) throws IOException {

        // checking if the level and it's map are legal
        if (level == null || level.getLevelMap() == null) throw new IOException("There is no level to save");

        // writing the level to file
        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(stream)));
        for (ArrayList<Tile> list : level.getLevelMap()){
            for (Tile tile : list){
                writer.print(tile.toString());
            }
            writer.println();
        }
        writer.close();
    }
}
