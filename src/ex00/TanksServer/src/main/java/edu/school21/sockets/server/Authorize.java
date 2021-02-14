package edu.school21.sockets.server;

import edu.school21.sockets.containers.Room;
import edu.school21.sockets.services.UsersService;

import java.io.*;
import java.net.Socket;

public class Authorize extends Thread {
    private Socket clientSocket;
    private UsersService usersService;
    private BufferedReader in;
    private BufferedWriter out;
    private String username;
    private boolean isAuthorize = false;
    private boolean isFirst = false;

    public Authorize(UsersService usersService, Socket clientSocket, boolean isFirst) {
        this.isFirst = isFirst;
        this.usersService = usersService;
        this.clientSocket = clientSocket;
        if (isFirst)
            Room.setFirstPlayerConnected(true);
        else
            Room.setSecondPlayerConnected(true);
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            while (!isAuthorize) {
                out.write("What do you want, signUp or signIn?\n");
                out.flush();
                String operationType = in.readLine();
                while (!operationType.equals("signUp") && !operationType.equals("signIn")) {
                    out.write("Wrong operation, try again: \n");
                    out.flush();
                    operationType = in.readLine();
                }
                if (operationType.equals("signUp")) {
                    isAuthorize = register();
                }
                if (operationType.equals("signIn")) {
                    isAuthorize = logIn();
                }
            }
            usersService.fillRoom(username, isFirst);
            if (isFirst)
                Room.setClient1(clientSocket);
            else
                Room.setClient2(clientSocket);
            System.out.println(isFirst + "player connnected");
        } catch (IOException e) {
            if (isFirst)
                Room.setPlayer1(0L);
            else
                Room.setPlayer2(0L);
            e.printStackTrace();
        }
    }

    private boolean register() throws IOException {
        String pass = readFromClient();
        if (usersService.signUp(username, pass)) {
            out.write("Successful!\n");
            out.flush();
            return true;
        } else {
            out.write("User already exist!\n");
            out.flush();
            return false;
        }
    }

    private boolean logIn() throws IOException {
        String pass = readFromClient();
        if (usersService.signIn(username, pass)) {
            out.write("Successful!\n");
            out.flush();
            return true;
        } else {
            out.write("Login or password are incorrect!\n");
            out.flush();
            return false;
        }
    }

    private String readFromClient() throws IOException {
        out.write("Enter username:\n");
        out.flush();
        username = in.readLine();
        out.write("Enter password:\n");
        out.flush();
        String password = in.readLine();
        return password;
    }
}
