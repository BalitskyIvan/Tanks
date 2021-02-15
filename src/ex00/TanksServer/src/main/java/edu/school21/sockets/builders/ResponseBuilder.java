package edu.school21.sockets.builders;

import edu.school21.sockets.models.Bullet;
import edu.school21.sockets.models.Tank;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

@Component
public class ResponseBuilder {

    public ResponseBuilder() {
    }

    public void sendTickToClient(BufferedWriter out, int tick, Tank userTank, Tank enemyTank, ArrayList<Bullet> myBullets, ArrayList<Bullet> enemyBullet, int reverse) throws IOException {
        JSONObject res = new JSONObject();

        JSONObject bulletObj = new JSONObject();
        for (Bullet bullet: enemyBullet)
        {
            JSONObject b = new JSONObject();
            b.put("x", bullet.getRectangle().x);
            b.put("y", reverse - bullet.getRectangle().y);
            bulletObj.put(String.valueOf(enemyBullet.indexOf(bullet)), b);
        }
        for (Bullet bullet: myBullets)
        {
            JSONObject b = new JSONObject();
            b.put("x", bullet.getRectangle().x);
            b.put("y", reverse - bullet.getRectangle().y);
            bulletObj.put(String.valueOf(enemyBullet.indexOf(bullet)), b);
        }
        res.put("tick", tick);
        res.put("myTank", userTank.toJson(reverse));
        res.put("enemyTank", enemyTank.toJson(reverse));
        res.put("bullets", bulletObj);
        out.write(res.toString() + "\n");
        out.flush();
    }

}
