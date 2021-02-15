package edu.school21.sockets.server;

import edu.school21.sockets.builders.ClientAction;
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
import java.util.List;

public class Game extends Thread {

    private ArrayList<Bullet> firstPlayerBullet, secondPlayerBullet;
    private User player1, player2;
    private Tank tank1, tank2;
    private BufferedReader in1, in2;
    private BufferedWriter out1, out2;
    private Socket socket1, socket2;
    private ClientAction clientAction1, clientAction2;
    private ResponseBuilder responseBuilder;
    private RequestParser requestParser;
    private static final float ARENA_HEIGHT = 840, ARENA_WIDTH = 840, TANK_FIRST_POS_Y = 730, TANK_FIRST_POS_X = 359;
    private int tick;

    public Game(UsersService usersService, RequestParser requestParser, ResponseBuilder responseBuilder) {
        this.requestParser = requestParser;
        this.responseBuilder = responseBuilder;
        firstPlayerBullet = new ArrayList<>();
        secondPlayerBullet = new ArrayList<>();
        socket1 = Room.getClient1();
        socket2 = Room.getClient2();
        tank1 = new Tank(TANK_FIRST_POS_X, TANK_FIRST_POS_Y);
        tank2 = new Tank(TANK_FIRST_POS_X, ARENA_HEIGHT - TANK_FIRST_POS_Y + Tank.getHEIGHT());
        ///удалить потом!
        firstPlayerBullet.add(new Bullet(tank1.getRectangle().x, tank2.getRectangle().y));
        firstPlayerBullet.add(new Bullet(tank1.getRectangle().x, tank2.getRectangle().y));
        secondPlayerBullet.add(new Bullet(tank2.getRectangle().x, tank1.getRectangle().y));
        secondPlayerBullet.add(new Bullet(tank2.getRectangle().x, tank1.getRectangle().y));

        tick = 0;
    }

    @Override
    public void run() {
        try {
            in1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
            out1 = new BufferedWriter(new OutputStreamWriter(socket1.getOutputStream()));
            in2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
            out2 = new BufferedWriter(new OutputStreamWriter(socket2.getOutputStream()));
            System.out.println("game started");
            out1.write("game\n");
            out1.flush();
            out2.write("game\n");
            out2.flush();
            while (tank1.getHp() > 0 && tank2.getHp() > 0)
            {
                responseBuilder.sendTickToClient(out1, tick, tank1, tank2, firstPlayerBullet, secondPlayerBullet, 0);
                clientAction1 = requestParser.method(in1);
                responseBuilder.sendTickToClient(out2, tick, tank2, tank1, secondPlayerBullet, firstPlayerBullet, (int)ARENA_HEIGHT);
                clientAction2 = requestParser.method(in2);
                tick++;
                actionHandler(clientAction1, tank1, firstPlayerBullet);
                actionHandler(clientAction2, tank2, secondPlayerBullet);
                updateBullets(firstPlayerBullet, tank1, true);
                updateBullets(secondPlayerBullet, tank2, false);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateBullets(List<Bullet> bullets, Tank tank, boolean isFirst)
    {
        for (Bullet bullet : bullets)
        {
            if (isFirst)
                bullet.moveUp();
            else
                bullet.moveDown();
            if (tank.isGetShoot(bullet.getRectangle()))
                bullets.remove(bullet);
        }
        for (Bullet bullet : bullets)
        {
           if (bullet.getRectangle().y > ARENA_HEIGHT || bullet.getRectangle().y < 0)
               bullets.remove(bullet);
        }
    }

    private boolean actionHandler(ClientAction clientAction, Tank tank, ArrayList<Bullet> bullets)
    {
        switch (clientAction)
        {
            case LEFT: {
                System.out.println("LEFT");
                tank.moveLeft();
                break;
            }
            case RIGHT: {
                System.out.println("RIGHT");
                tank.moveRight();
                break;
            }
            case SHOOT: {
                System.out.println("SHOOT");

                bullets.add(new Bullet(tank.getRectangle().x, tank.getRectangle().y));
                break;
            }
            case OFFLINE:
                return false;
            default:
                return true;
        }
        return true;
    }
}
