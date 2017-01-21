package view;

import controller.displayer.Displayer;

public interface View {
    void display();
    Displayer getDisplayer();
    void passException(Exception e);
}
