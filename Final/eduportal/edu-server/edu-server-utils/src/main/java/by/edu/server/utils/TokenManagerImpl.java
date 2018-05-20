package by.edu.server.utils;

import by.edu.server.beans.people.User;
import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenManagerImpl implements TokenManager {
    private static Logger logger = LogManager.getLogger(TokenManagerImpl.class);
    private Map<String, User> tokens = new HashMap<>();

    public static String hash(String data) throws NoSuchAlgorithmException {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(data.getBytes());

            byte byteData[] = messageDigest.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte aByteData : byteData) {
                String hex = Integer.toHexString(0xff & aByteData);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            TokenManagerImpl.logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }

    }

    @Override
    public Boolean check(String token) {
        return tokens.containsKey(token);
    }

    @Override
    public User get(String token) {
        return tokens.get(token);
    }

    @Override
    public String add(User user) {
        String token = Jwts.builder().setId(((Long) System.nanoTime()).toString() + user.getUsername() + ((Long) System.nanoTime()).toString()).compact();
        tokens.put(token, user);
        return token;
    }

    @Override
    public void delete(String token) {
        tokens.remove(token);
    }
}
