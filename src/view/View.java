package view;

import controller.displayer.Displayer;

import java.util.LinkedList;

public interface View {
    void display();
    Displayer getDisplayer();
    void passException(Exception e);
    void winMessage(String message);
    void setCurrentLevel(String levelName);
}
