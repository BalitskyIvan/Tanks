package edu.school21.sockets.services;

import edu.school21.sockets.containers.Room;
import edu.school21.sockets.exceptions.EntityNotFoundException;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Optional;

@Component
public class UsersServiceImpl implements UsersService {

    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean signUp(String email, String password) {
        Optional<User> user = null;
        try {
            user = usersRepository.findByUsername(email);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (user.isPresent()) {
            return false;
        } else {
            String pass = passwordEncoder.encode(password);
            usersRepository.save(new User(3L, email, pass));
            return true;
        }
    }

    @Override
    public boolean signIn(String email, String password) {
        Optional<User> user = null;
        try {
            user = usersRepository.findByUsername(email);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (user.isPresent()) {
            if (passwordEncoder.matches(password, user.get().getPassword()))
                return true;
        }
        return false;
    }

    @Override
    public void fillRoom(String username, boolean isFirst) {
        if (isFirst) {
            Room.setPlayer1(usersRepository.findId(username));
        } else {
            Room.setPlayer2(usersRepository.findId(username));
        }
    }
}
