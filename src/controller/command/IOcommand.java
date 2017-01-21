package controller.command;

import model.Model;
import model.data.level.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

/* defines the general functionality of a load/save command */
public abstract class IOcommand implements Command {

    protected String filePath=null;
    protected Model model=null;
    protected HashMap<String, LevelLoader> loaderExtensions=null;
    protected HashMap<String, LevelSaver> saverExtensions=null;

    public IOcommand(String filePath, Model model) throws IOException {
        setFilePath(filePath);
        setModel(model);
        setDefaultExtension();
    }

    public IOcommand(String filePath) {
        this.filePath = filePath;
    }

    public IOcommand() {
        setDefaultExtension();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) throws FileNotFoundException {
        if (filePath==null)
            throw new FileNotFoundException("Please provide a valid path");
        this.filePath = filePath;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) throws IOException {
        if (model==null)
            throw new IOException("Please provide a valid level");
        this.model = model;
    }

    protected void setDefaultExtension(){
        loaderExtensions = new HashMap<>();
        loaderExtensions.put("txt",new MyTextLevelLoader());
        loaderExtensions.put("obj",new MyObjectLevelLoader());
        loaderExtensions.put("xml",new MyXMLLevelLoader());

        saverExtensions = new HashMap<>();
        saverExtensions.put("txt", new MyTextLevelSaver());
        saverExtensions.put("obj", new MyObjectLevelSaver());
        saverExtensions.put("xml", new MyXMLLevelSaver());
    }

    public HashMap<String, LevelLoader> getLoaderExtensions() {
        return loaderExtensions;
    }

    public void setLoaderExtensions(HashMap<String, LevelLoader> loaderExtensions) {
        this.loaderExtensions = loaderExtensions;
    }

    public HashMap<String, LevelSaver> getSaverExtensions() {
        return saverExtensions;
    }

    public void setSaverExtensions(HashMap<String, LevelSaver> saverExtensions) {
        this.saverExtensions = saverExtensions;
    }
}
