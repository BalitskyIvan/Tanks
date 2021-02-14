package edu.school21.application;

import edu.school21.config.ServerApplicationConfig;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import edu.school21.repositories.UsersRepositoryImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ServerApplicationConfig.class);

        UsersRepository rep = context.getBean("usersRepositoryImpl", UsersRepositoryImpl.class);

//        User user = new User(7L, "user", "pass");
//        rep.save(user);
//        rep.save(new User(7L, "user1", "pass2"));
//        System.out.println(rep.findByUsername(user.getUsername()).orElse(null));
//        rep.update(user);
//        rep.delete(user.getUsername());
//        System.out.println(rep.findAll());

    }

}
