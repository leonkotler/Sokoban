package controller;

import controller.command.Command;

import java.util.Observer;

public interface Controller extends Observer {
    void insertCommand(Command command) throws InterruptedException;

    void start();

    void stop();
}
