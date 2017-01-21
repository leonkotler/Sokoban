package controller.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer{

    protected int port;
    protected ClientHandler clientHandler;
    protected volatile boolean stop;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public MyServer(int port, ClientHandler clientHandler) {
        this.port = port;
        this.clientHandler = clientHandler;
        stop = false;
    }

    public void runServer() throws IOException {

        ServerSocket server = new ServerSocket(port);
        System.out.println("Server is alive and listening for connections");
        server.setSoTimeout(10000);

        while(!stop){
            try{
                Socket aClient=server.accept();
                System.out.println("User connected");

                clientHandler.handleClient(aClient.getInputStream(),aClient.getOutputStream());

                aClient.getInputStream().close();
                aClient.getOutputStream().close();
                aClient.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            System.out.println("Shutting down server");
            server.close();
            System.out.println("Server closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start()   {
        new Thread(new Runnable() {
            public void run() {
                try {
                    runServer();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }).start();
    }

    public void stop() {
        stop = true;
    }
}
