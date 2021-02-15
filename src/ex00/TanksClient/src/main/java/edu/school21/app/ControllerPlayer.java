package edu.school21.app;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class ControllerPlayer extends Thread {
    private BufferedWriter out;
    private BufferedReader in;
    private GamePane gamePane;

    public ControllerPlayer(BufferedWriter out, BufferedReader in, GamePane gamePane) {
        this.out = out;
        this.in = in;
        this.gamePane = gamePane;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String str = in.readLine();
                JSONObject jsonObject = new JSONObject(str);
                String response = jsonObject.getInt("tick") + ":";
                int movement = gamePane.getMovement();
                switch (movement)
                {
                    case 1:
                        response += "LEFT";
                        break;
                    case 2:
                        response += "RIGHT";
                        break;
                    case 3:
                        response += "SHOOT";
                        break;
                    default:
                        response += "NONE";
                }
                out.write(response + "\n");
                out.flush();
                JSONObject enemyTank = new JSONObject(jsonObject.get("enemyTank").toString());
                JSONObject myTank = new JSONObject(jsonObject.get("myTank").toString());
                gamePane.update(myTank.getInt("x"), enemyTank.getInt("x"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
