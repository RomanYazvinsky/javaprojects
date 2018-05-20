package by.edu.server.utils;

import by.edu.server.beans.people.User;
import org.apache.logging.log4j.Level;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public interface TokenManager {


    Boolean check(String token);

    User get(String token);

    String add(User user);

    void delete(String token);
}
