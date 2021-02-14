package edu.school21.sockets;

import java.io.*;
import java.net.Socket;

public class Main {

    private static Socket clientSocket;
    private static BufferedReader reader;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static String serverWord;
    private static String myMsg;

    public static void main(String[] args) {
        int port = 0;
        try {
            port = Integer.parseInt(args[0].substring(14));
            if (!args[0].equals("--server-port=" + port)) {
                System.err.println("IllegalArguments");
                System.exit(-1);
            }
        } catch (Exception e) {
            System.err.println("IllegalArguments");
            System.exit(-1);
        }
        try {
            try {
                clientSocket = new Socket("localhost", port);
                reader = new BufferedReader(new InputStreamReader(System.in));
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                while (true) {
                    if (in.ready())
                    {
                       serverWord = in.readLine();
                       System.out.println(serverWord);
                    }
                    if (reader.ready()) {
                        myMsg = reader.readLine();
                        out.write(myMsg + "\n");
                        out.flush();
                        if (myMsg.equals("EXIT"))
                            break;
                    }
                }
            } finally {
                System.out.println("Connection closed.");
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }
}
