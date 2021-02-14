package edu.school21.sockets.app;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        int port = 0;
        try
        {
            port = Integer.parseInt(args[0].substring(7));
            if (!args[0].equals("--port=" + port))
            {
                System.err.println("IllegalArguments");
                System.exit(-1);
            }
        } catch (Exception e)
        {
            System.err.println("IllegalArguments");
            System.exit(-1);
        }
        ApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
        Server server = context.getBean("server", Server.class);
        server.setPort(port);
        server.start();
    }
}
