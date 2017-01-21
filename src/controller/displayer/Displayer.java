package controller.displayer;
import model.Model;

import java.io.IOException;

public interface Displayer {
    void display() throws IOException;
    void setModel(Model model);
}
