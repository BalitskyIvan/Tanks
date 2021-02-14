package edu.school21.services;

import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository repository;

    @Autowired
    public UsersServiceImpl(UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public String signUp(String username, String password) {
        Optional<User> opt_user = repository.findByUsername(username);
        if (opt_user.isPresent()) {
            return "User " + username + " is already exist";
        } else {
            repository.save(new User(0L, username, password));
            return "User " + username + " created successfully";
        }
    }
}
