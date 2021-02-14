package edu.school21.sockets.services;

import edu.school21.sockets.containers.Room;

public interface UsersService {
    boolean signUp(String email, String password);
    boolean signIn(String email, String password);
    void fillRoom(String username, boolean isFirst);
}
