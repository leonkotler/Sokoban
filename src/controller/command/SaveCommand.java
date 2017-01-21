package controller.command;

import model.Model;
import utils.FilePathUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

/* SaveCommand saves a level using it's appropriate saver based on the file's extension */
public class SaveCommand extends IOcommand{

    public SaveCommand(String filePath, Model model) throws IOException {
        super(filePath,model);
    }

    public SaveCommand(Model model) throws IOException {
        this.model=model;
    }
    public SaveCommand() {
    }

    @Override
    public void execute() throws IOException{
        // checking if there's a level to save
        if (model.getCurrentLvl()==null)
            throw new IOException("No level found, try loading one first");

        // getting the extension from the file path
        FilePathUtil checker = new FilePathUtil();
        String ext = checker.getExtension(filePath);

        if (!saverExtensions.containsKey(ext))
            throw new IOException("Please provide a valid file format");
        else
            saverExtensions.get(ext).saveLevel(model.getCurrentLvl(),new FileOutputStream(filePath));

    }

    @Override
    public void setParams(LinkedList<String> params) throws IOException {
        setFilePath(params.getFirst());
    }
}
