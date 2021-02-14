package edu.school21.sockets.server;

import edu.school21.sockets.builders.RequestParser;
import edu.school21.sockets.builders.ResponseBuilder;
import edu.school21.sockets.containers.Room;
import edu.school21.sockets.models.Bullet;
import edu.school21.sockets.models.Tank;
import edu.school21.sockets.models.User;
import edu.school21.sockets.services.UsersService;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Game extends Thread {

    private ArrayList<Bullet> firstPlayerBullet, secondPlayerBullet;
    private User player1, player2;
    private Tank tank1, tank2;
    private BufferedReader in1, in2;
    private BufferedWriter out1, out2;
    private Socket socket1, socket2;
    private ResponseBuilder responseBuilder;
    private RequestParser requestParser;
    private static final float ARENA_HEIGHT = 840, ARENA_WIDTH = 840, TANK_FIRST_POS_Y = 730, TANK_FIRST_POS_X = 359;
    private int tick;
    public Game(UsersService usersService, RequestParser requestParser, ResponseBuilder responseBuilder) {
        this.requestParser = requestParser;
        this.responseBuilder = responseBuilder;
        socket1 = Room.getClient1();
        socket2 = Room.getClient2();
        tank1 = new Tank(TANK_FIRST_POS_X, TANK_FIRST_POS_Y);
        tank2 = new Tank(TANK_FIRST_POS_X, ARENA_HEIGHT - TANK_FIRST_POS_Y + Tank.getHEIGHT());
        tick = 0;
    }

    @Override
    public void run() {
        try {
            in1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
            out1 = new BufferedWriter(new OutputStreamWriter(socket1.getOutputStream()));
            in2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
            out2 = new BufferedWriter(new OutputStreamWriter(socket2.getOutputStream()));
            out1.write("GAME\n");
            out1.flush();
            out2.write("GAME\n");
            out2.flush();
            while (tank1.getHp() > 0 && tank2.getHp() > 0)
            {
                responseBuilder.sendTickToClient(out1, tick, tank1, tank2, firstPlayerBullet, secondPlayerBullet);
                responseBuilder.sendTickToClient(out2, tick, tank2, tank1, secondPlayerBullet, firstPlayerBullet);

                tick++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
