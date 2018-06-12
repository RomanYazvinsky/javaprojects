package by.edu.server.utils;

import by.edu.server.beans.users.User;
import by.edu.server.utils.api.TokenManager;
import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Component
public class TokenManagerImpl implements TokenManager {
    private static Logger logger = LogManager.getLogger(TokenManagerImpl.class);
    private Map<String, ExpirationValidator> tokens = new HashMap<>();
    private long timeOut = 10000;

    private boolean checkExpiration(String currentToken) {
        List<String> toDelete = new ArrayList<>();
        boolean result = false;
        if (!tokens.containsKey(currentToken)){
            return true;
        }
        for (String token : tokens.keySet()) {
            if (tokens.get(token).getExpiration().before(new Date())) {
                toDelete.add(token);
                if (token.equals(currentToken)) {
                    result = true;
                }
            }
        }
        for (String token : toDelete) {
            tokens.remove(token);
        }
        return result;
    }

    @Override
    public Boolean check(String token) {
        boolean result = !checkExpiration(token);
        if (result) {
            tokens.get(token).setExpiration(new Date(System.currentTimeMillis() + timeOut));
        }
        return result;
    }

    @Override
    public User get(String token) {
        boolean result = !checkExpiration(token);
        if (result) {
            tokens.get(token).setExpiration(new Date(System.currentTimeMillis() + timeOut));
            return tokens.get(token).getUser();
        }
        return null;
    }

    @Override
    public String add(User user) {
        Date expiration = new Date(System.currentTimeMillis() + timeOut);
        String token = Jwts.builder().setId(((Long) System.nanoTime()).toString() + user.getUsername() + ((Long) System.nanoTime()).toString())
                .setExpiration(new Date(System.currentTimeMillis() + timeOut)).compact();
        ExpirationValidator expirationValidator = new ExpirationValidator();
        expirationValidator.setUser(user);
        expirationValidator.setExpiration(expiration);
        tokens.put(token, expirationValidator);
        return token;
    }

    @Override
    public void delete(String token) {
        tokens.remove(token);
    }

    @Override
    public String hash(String data) throws NoSuchAlgorithmException {
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
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

}
