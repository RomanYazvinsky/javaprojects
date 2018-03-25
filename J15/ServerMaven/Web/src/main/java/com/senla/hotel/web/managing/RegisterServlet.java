package com.senla.hotel.web.managing;

import com.senla.hotel.entities.User;
import com.senla.hotel.exceptions.InternalErrorException;
import com.senla.hotel.facade.Facade;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import utilities.parsers.JsonEntityParser;
import utilities.web.TokenManager;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

import static com.senla.hotel.constants.Constants.*;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static Logger logger = LogManager.getLogger(RegisterServlet.class);

    public void doPost(HttpServletRequest req, HttpServletResponse res){
        Facade facade = Facade.getInstance();
        try {
            res.setContentType(APPLICATION_JSON);
            JSONObject parameters = new JSONObject(JsonEntityParser.parseBuffer(req.getReader()));
            String username = parameters.getString(USERNAME);
            String password = parameters.getString(PASSWORD);
            if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
                res.getWriter().println(BAD_LOGIN_PASSWORD);
                return;
            }
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            facade.register(user);
            JSONObject response = new JSONObject();
            response.append(USERNAME, user.getUsername());
            response.append(PASSWORD, user.getPassword());
            res.getWriter().println(response);
        } catch (InternalErrorException | IOException e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
    }
}
