package edu.school21.app;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ControllerPlayer extends Thread {
    private BufferedWriter out;
    private BufferedReader in;
    private GamePane gamePane;
    private int lastTickToShoot = 0;

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
                if (str.equals("stop")) {

                }
                JSONObject jsonObject = new JSONObject(str);
                int tick = jsonObject.getInt("tick");
                String response = tick + ":";
                int movement = gamePane.getMovement();
                switch (movement)
                {
                    case 1:
                        response += "LEFT";
                        break;
                    case 2:
                        response += "RIGHT";
                        break;
                    case 3: {
                        if (tick - lastTickToShoot > 20 || lastTickToShoot == 0) {
                            response += "SHOOT";
                            lastTickToShoot = tick;
                        }
                        else
                            response += "NONE";
                        break;
                    }
                    default:
                        response += "NONE";
                }
                out.write(response + "\n");
                out.flush();
                JSONObject enemyTank = new JSONObject(jsonObject.get("enemyTank").toString());
                JSONObject myTank = new JSONObject(jsonObject.get("myTank").toString());
                int enBulletSize = jsonObject.getInt("enBulletSize");
                int myBulletSize = jsonObject.getInt("myBulletSize");

                JSONObject enemyBulletsObject = new JSONObject(jsonObject.get("enBullets").toString());
                Integer [][] arrayBulletEnemy = new Integer[3][enBulletSize];
                for (int i = 0; i < enBulletSize; i++) {
                    JSONObject bulletE = new JSONObject(enemyBulletsObject.get(String.valueOf(i)).toString());
                    arrayBulletEnemy[0][i] = bulletE.getInt("id");
                    arrayBulletEnemy[1][i] = bulletE.getInt("x");
                    arrayBulletEnemy[2][i] = bulletE.getInt("y");
                }

                JSONObject myBulletsObject = new JSONObject(jsonObject.get("myBullets").toString());
                Integer [][] arrayBulletMy = new Integer[3][myBulletSize];
                for (int i = 0; i < myBulletSize; i++)
                {
                    JSONObject bulletM =  new JSONObject(myBulletsObject.get(String.valueOf(i)).toString());
                    arrayBulletMy[0][i] = bulletM.getInt("id");
                    arrayBulletMy[1][i] = bulletM.getInt("x");
                    arrayBulletMy[2][i] = bulletM.getInt("y");
                }
                gamePane.update(myTank.getInt("x"), enemyTank.getInt("x"), enBulletSize, myBulletSize, arrayBulletEnemy, arrayBulletMy, myTank.getInt("hp"), enemyTank.getInt("hp"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
