package controller.command;


import model.Model;
import utils.FilePathUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

/* LoadCommand loads a file (supports different extensions) and stores is within itself. The level is accessible through getLoadedLevel() */
public class LoadCommand extends IOcommand{
    Model model;

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public LoadCommand(String filePath, Model model) throws IOException {
       super(filePath);
       this.model=model;
    }
    public LoadCommand(Model model) throws IOException {
        this.model=model;
    }
    public LoadCommand() {
    }

    @Override
    public void execute() throws IOException {

        FilePathUtil checker = new FilePathUtil();
        String ext = checker.getExtension(filePath);

        if (!loaderExtensions.containsKey(ext))
            throw new IOException("Please provide a valid file format");
        else
            try {
                model.setCurrentLvl(loaderExtensions.get(ext).loadLevel(new FileInputStream(filePath)));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void setParams(LinkedList<String> params) throws FileNotFoundException {
        setFilePath(params.getFirst());
    }
}
