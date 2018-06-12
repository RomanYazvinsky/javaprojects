package by.edu.server.utils.api;

import by.edu.server.beans.users.User;

import java.security.NoSuchAlgorithmException;

public interface TokenManager {


    Boolean check(String token);

    User get(String token);

    String add(User user);

    void delete(String token);

    String hash(String data) throws NoSuchAlgorithmException;
}
