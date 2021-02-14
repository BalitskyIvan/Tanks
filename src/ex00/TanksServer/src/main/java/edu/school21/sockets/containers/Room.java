package edu.school21.sockets.containers;

import org.springframework.stereotype.Component;

import java.net.Socket;

public final class Room {
    private static Long player1 = 0L;
    private static boolean firstPlayerConnected = false;
    private static Long player2 = 0L;
    private static boolean secondPlayerConnected = false;
    private static boolean isGameStarted = false;
    private static Socket client1;
    private static Socket client2;

    private static Room instance;

    private Room() {
    }

    public static Room getInstance() {
        if (instance == null) {
            instance = new Room();
        }
        return instance;
    }

    public static boolean isRoomEmpty()
    {
        return (getPlayer1() == 0L || getPlayer2() == 0L);
    }

    public static Socket getClient1() {
        return client1;
    }

    public static void setClient1(Socket client1) {
        Room.client1 = client1;
    }

    public static Socket getClient2() {
        return client2;
    }

    public static void setClient2(Socket client2) {
        Room.client2 = client2;
    }

    public static boolean isFirstPlayerConnected() {
        return firstPlayerConnected;
    }

    public static void setFirstPlayerConnected(boolean firstPlayerConnected) {
        Room.firstPlayerConnected = firstPlayerConnected;
    }

    public static boolean isSecondPlayerConnected() {
        return secondPlayerConnected;
    }

    public static void setSecondPlayerConnected(boolean secondPlayerConnected) {
        Room.secondPlayerConnected = secondPlayerConnected;
    }

    public static boolean isIsGameStarted() {
        return isGameStarted;
    }

    public static void setIsGameStarted(boolean isGameStarted) {
        Room.isGameStarted = isGameStarted;
    }

    public static Long getPlayer1() {
        return player1;
    }

    public static void setPlayer1(Long player1) {
        Room.player1 = player1;
    }

    public static Long getPlayer2() {
        return player2;
    }

    public static void setPlayer2(Long player2) {
        Room.player2 = player2;
    }
}

