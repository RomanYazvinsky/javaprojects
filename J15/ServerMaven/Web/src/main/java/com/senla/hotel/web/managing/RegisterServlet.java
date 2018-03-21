package com.senla.hotel.web.managing;

import com.senla.hotel.entities.User;
import com.senla.hotel.exceptions.InternalErrorException;
import com.senla.hotel.facade.Facade;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
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

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        Facade facade = Facade.getInstance();
        try {
            res.setContentType(APPLICATION_JSON);
            String username = req.getParameter(USERNAME);
            String password = req.getParameter(PASSWORD);
            User user;
            password = TokenManager.hash(password);
            user = new User();
            user.setUsername(username);
            user.setPassword(password);
            PrintWriter printWriter = res.getWriter();
            if (!facade.register(user)) {
                printWriter.println(REGISTRATION_FAILURE);
            }
            JSONObject object = new JSONObject();
            object.append(USERNAME, user.getUsername());
            object.append(PASSWORD, user.getPassword());
            printWriter.println(object);
        } catch (InternalErrorException | NoSuchAlgorithmException e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
    }
}
