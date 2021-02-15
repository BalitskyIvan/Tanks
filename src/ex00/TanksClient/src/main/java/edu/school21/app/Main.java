package edu.school21.app;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;


public class Main extends Application {
    private static Stage primaryStage;
    private GamePane gamePane;
    private static Socket clientSocket;
    private static BufferedReader in;
    private static BufferedWriter out;

    @FXML
    private Button signup, signin;
    @FXML
    private TextField ip, password, username;

    public void initialize(){
        signup.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String host = ip.getCharacters().toString().substring(0, ip.getCharacters().toString().indexOf(':'));
                String portStr = ip.getCharacters().toString().substring(ip.getCharacters().toString().indexOf(':') + 1);
                int port = Integer.parseInt(portStr);
                try {
                    clientSocket = new Socket(host, port);
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String response = in.readLine();
                    JSONObject jsonObject = new JSONObject() {{
                            put("type", "signup");
                            put("username", username.getCharacters());
                            put("password", password.getCharacters());
                        }};
                    out.write(jsonObject.toString() + "\n");
                    out.flush();
                    response = in.readLine();
                    if (response.equals("game")) {
                        stageGame();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        signin.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String host = ip.getCharacters().toString().substring(0, ip.getCharacters().toString().indexOf(':'));
                String portStr = ip.getCharacters().toString().substring(ip.getCharacters().toString().indexOf(':') + 1);
                int port = Integer.parseInt(portStr);
                try {
                    clientSocket = new Socket(host, port);
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String response = in.readLine();
                    JSONObject jsonObject = new JSONObject() {{
                        put("type", "signup");
                        put("username", username.getCharacters());
                        put("password", password.getCharacters());
                    }};
                    out.write(jsonObject.toString() + "\n");
                    out.flush();
                    response = in.readLine();
                    if (response.equals("game")) {
                        stageGame();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void stageGame() {
        gamePane = new GamePane();
        Scene scene = new Scene(gamePane, 840, 840);
        scene.getStylesheets().addAll(this.getClass().getResource("/style.css").toExternalForm());
        scene.setOnKeyPressed(event -> gamePane.getKeys().put(event.getCode(), true));
        scene.setOnKeyReleased(event -> gamePane.getKeys().put(event.getCode(), false));
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gamePane.updateKeys();
            }
        };
        timer.start();

        getPrimaryStage().setScene(scene);
        getPrimaryStage().setResizable(false);
        getPrimaryStage().show();
        Thread control = new ControllerPlayer(out, in, gamePane);
        control.start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        getIconAndTitle(primaryStage);

        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        Scene scene1 = new Scene(root);
        scene1.setFill(Color.TRANSPARENT);

        primaryStage.setScene(scene1);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.show();
        this.primaryStage = primaryStage;
    }

    private void getIconAndTitle(Stage primaryStage) {
        InputStream iconStream = getClass().getResourceAsStream("/png/tank.png");
        Image image = new Image(iconStream);
        primaryStage.getIcons().add(image);
        primaryStage.setTitle("Tanks");
    }
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    public static void main(String[] args) {

        launch(args);
    }
}
