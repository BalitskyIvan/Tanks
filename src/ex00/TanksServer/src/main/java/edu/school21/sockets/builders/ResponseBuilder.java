package edu.school21.sockets.builders;

import edu.school21.sockets.models.Bullet;
import edu.school21.sockets.models.Tank;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class ResponseBuilder {

    public ResponseBuilder() {
    }

    public void sendTickToClient(BufferedWriter out, int tick, Tank userTank, Tank enemyTank, ArrayList<Bullet> myBullets, ArrayList<Bullet> enemyBullet)
    {
        JSONObject res = new JSONObject();
        ArrayList<Bullet> bullets = new ArrayList<>();
        bullets.addAll(myBullets);
        bullets.addAll(enemyBullet);
        try {
            res.put("tick", tick);
            res.put("myTank", userTank);
            res.put("enemyTank", enemyTank);
            res.put("bullets", bullets);
            out.write(res.toString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
