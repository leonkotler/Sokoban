package controller.command;


import model.Model;
import utils.FilePathUtil;
import view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

/* LoadCommand loads a file (supports different extensions) and stores is within itself. The level is accessible through getLoadedLevel() */
public class LoadCommand extends IOcommand{
    Model model;
    View view;

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
    public LoadCommand(Model model,View view) throws IOException {
        this.model=model;
        this.view=view;
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
                if (model.getCurrentLvl().getLevelName()==null){

                    Path p = Paths.get(filePath);
                    String fileName = p.getFileName().toString();

                    if (fileName.indexOf(".") > 0) {
                        fileName = fileName.substring(0, fileName.lastIndexOf("."));
                    }
                    model.getCurrentLvl().setLevelName(fileName);
                    view.setCurrentLevel(fileName);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void setParams(LinkedList<String> params) throws FileNotFoundException {
        if (params.size()==0)
            throw new FileNotFoundException("Please provide a file path");
        setFilePath(params.getFirst());
    }
}
