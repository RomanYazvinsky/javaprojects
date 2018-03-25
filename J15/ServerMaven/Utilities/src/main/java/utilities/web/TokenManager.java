package utilities.web;

import com.senla.hotel.entities.User;
import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class TokenManager {
    private static Logger logger = LogManager.getLogger(TokenManager.class);

    private static TokenManager instance = new TokenManager();
    private Map<String, User> tokens;

    private TokenManager() {
        tokens = new HashMap<>();
    }

    public static TokenManager getInstance() {
        return instance;
    }

    public static String hash(String data) throws NoSuchAlgorithmException {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(data.getBytes());

            byte byteData[] = messageDigest.digest();

            StringBuffer hexString = new StringBuffer();
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

    public Boolean check(String token) {
        return tokens.containsKey(token);
    }

    public User get(String token) {
        return tokens.get(token);
    }

    public String add(User user) {
        String token = Jwts.builder().setId(user.getUsername()).setSubject(((Long)System.nanoTime()).toString()).compact();
        tokens.put(token, user);
        return token;
    }

    public void delete(String token) {
        tokens.remove(token);
    }
}
