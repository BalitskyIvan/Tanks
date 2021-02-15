package edu.school21.sockets.server;

import edu.school21.sockets.containers.Room;
import edu.school21.sockets.services.UsersService;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;

public class Authorize extends Thread {
    private Socket clientSocket;
    private UsersService usersService;
    private BufferedReader in;
    private BufferedWriter out;
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
            System.out.println("welcome");
            out.write("welcome\n");
            out.flush();
            JSONObject jsonObject = null;
            while (!isAuthorize) {
                String response = in.readLine();
                jsonObject = new JSONObject(response);
                if (jsonObject.get("type").toString().equals("signup"))
                {
                    isAuthorize = usersService.signIn(jsonObject.get("username").toString(), jsonObject.getString("password"));
                    if (!isAuthorize)
                    {
                        out.write("error: 0\n");
                        out.flush();
                    }
                }
                if (jsonObject.get("type").toString().equals("signin"))
                {
                    isAuthorize = usersService.signIn(jsonObject.get("username").toString(), jsonObject.getString("password"));
                    if (!isAuthorize)
                    {
                        out.write("error: 1\n");
                        out.flush();
                    }
                }
            }
            usersService.fillRoom(jsonObject.get("username").toString(), isFirst);
            if (isFirst)
                Room.setClient1(clientSocket);
            else
                Room.setClient2(clientSocket);
            System.out.println(jsonObject.get("username").toString() + " connnected");
        } catch (IOException e) {
            if (isFirst)
                Room.setPlayer1(0L);
            else
                Room.setPlayer2(0L);
            e.printStackTrace();
        }
    }
}
