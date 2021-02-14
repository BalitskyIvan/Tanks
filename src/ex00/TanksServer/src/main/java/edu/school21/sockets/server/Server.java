package edu.school21.sockets.server;

import edu.school21.sockets.builders.RequestParser;
import edu.school21.sockets.builders.ResponseBuilder;
import edu.school21.sockets.containers.Room;
import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class Server {

    private static Socket clientSocket;
    private static ServerSocket server;
    private final UsersService usersService;
    private ResponseBuilder responseBuilder;
    private RequestParser requestParser;
    private Thread game;
    private int port;

    @Autowired
    public Server(UsersService usersService) {
        this.usersService = usersService;
        Room.getInstance();
    }

    @Autowired
    public void setResponseBuilder(ResponseBuilder responseBuilder) {
        this.responseBuilder = responseBuilder;
    }

    @Autowired
    public void setRequestParser(RequestParser requestParser) {
        this.requestParser = requestParser;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void start() {
        try {
            server = new ServerSocket(port);
            while (true) {
                clientSocket = server.accept();
                if (Room.isIsGameStarted())
                    game.join();
                if (!Room.isFirstPlayerConnected() || !Room.isSecondPlayerConnected()) {
                    Thread auth = new Authorize(usersService, clientSocket, (!Room.isFirstPlayerConnected()));
                    auth.start();
                    auth.join();
                }
                if (!Room.isRoomEmpty() && !Room.isIsGameStarted())
                {
                    System.out.println("here");
                    Room.setIsGameStarted(true);
                    game = new Game(usersService, requestParser, responseBuilder);
                    game.start();
                }
            }
        } catch (IOException | InterruptedException ioException) {
            ioException.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
