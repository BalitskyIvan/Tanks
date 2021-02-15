
package edu.school21.sockets.builders;

import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class RequestParser {

//    public static void main(String[] args) {
//        BufferedReader br = new BufferedReader(new StringReader("138:RIGHT"));
//
//        try {
//            System.out.println(method(br));
//        } catch (IOException ioe) {
//            System.err.println(ioe.getMessage());
//        }
//    }

//    private static Long tick;

    public ClientAction method(BufferedReader br) throws IOException {
        long beginTime = System.currentTimeMillis();

        long rejectTime = 5000L;

        do {
            if (br.ready()) {
                String line = br.readLine();
//                tick = Long.parseLong(line.substring(0, line.lastIndexOf(":")));

                String action = line.substring(line.lastIndexOf(":") + 1);

                for (ClientAction ca : ClientAction.values()) {
                    if (ca.toString().equals(action)) {
                        return ca;
                    }
                }
            }
        } while ((System.currentTimeMillis() - beginTime) <= rejectTime);

        return ClientAction.OFFLINE;
    }

}