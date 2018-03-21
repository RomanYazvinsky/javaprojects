package com.senla.hotel.web.managing;

import com.senla.hotel.constants.Constants;
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

import static com.senla.hotel.constants.Constants.*;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static Logger logger = LogManager.getLogger(LogoutServlet.class);

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        Facade facade = Facade.getInstance();
        try {
            facade.log(req.getHeader(AUTHORIZATION), "LOGOUT: " + this.getClass().getName() + DO_GET);
            String token = req.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                res.getWriter().println("logout failure");
                logger.log(Level.DEBUG, "bad parameters");
                return;
            }
            TokenManager.getInstance().delete(token);
            res.setContentType(APPLICATION_JSON);
            JSONObject result = new JSONObject();
            result.append("token", token);
            res.getWriter().println(result);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            try {
                facade.log(req.getHeader(AUTHORIZATION), "LOGOUT: " + this.getClass().getName() + DO_GET_ERROR + e.getMessage());
            } catch (InternalErrorException e1) {
                logger.log(Level.DEBUG, e1.getMessage());
            }
        }
    }
}
