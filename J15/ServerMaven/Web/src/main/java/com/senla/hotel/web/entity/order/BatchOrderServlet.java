package com.senla.hotel.web.entity.order;

import com.senla.hotel.exceptions.InternalErrorException;
import com.senla.hotel.facade.Facade;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.senla.hotel.constants.Constants.*;

@WebServlet("/orders")
public class BatchOrderServlet extends HttpServlet {
    private static Logger logger = LogManager.getLogger(BatchOrderServlet.class);

    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        Facade facade = Facade.getInstance();
        try {
            facade.log(req.getHeader(AUTHORIZATION), this.getClass().getName() + DO_GET);
            JSONArray objects = null;
            objects = new JSONArray(facade.getOrders());
            res.getWriter().println(objects);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            try {
                facade.log(req.getHeader(AUTHORIZATION), this.getClass().getName() + DO_GET_ERROR + e.getMessage());
            } catch (InternalErrorException e1) {
                logger.log(Level.DEBUG, e1.getMessage());
            }
        }
    }
}
