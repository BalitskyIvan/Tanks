package edu.school21.services;

import org.springframework.stereotype.Service;

@Service
public interface UsersService {
    String signUp(String username, String password);
}
