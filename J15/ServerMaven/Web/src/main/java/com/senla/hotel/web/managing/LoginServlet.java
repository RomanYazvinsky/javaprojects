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
import java.security.NoSuchAlgorithmException;

import static com.senla.hotel.constants.Constants.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static Logger logger = LogManager.getLogger(LoginServlet.class);

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        Facade facade = Facade.getInstance();
        try {
            String username = req.getParameter(USERNAME);
            String password = req.getParameter(PASSWORD);
            password = TokenManager.hash(password);
            User user = facade.getUser(username, password);
            if (user == null) {
                res.getWriter().println(NO_SUCH_ACCOUNT);
                logger.log(Level.DEBUG, NO_SUCH_ACCOUNT);
                return;
            }
            String token = TokenManager.getInstance().add(user);
            res.setContentType(APPLICATION_JSON);
            JSONObject result = new JSONObject();
            result.append(TOKEN, token);
            res.getWriter().println(result);
        } catch (InternalErrorException | NoSuchAlgorithmException e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
    }
}
