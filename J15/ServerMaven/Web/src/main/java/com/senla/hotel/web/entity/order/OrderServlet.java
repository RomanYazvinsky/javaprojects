package com.senla.hotel.web.entity.order;

import com.senla.hotel.entities.Order;
import com.senla.hotel.exceptions.InternalErrorException;
import com.senla.hotel.facade.Facade;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import utilities.parsers.JsonEntityParser;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.senla.hotel.constants.Constants.*;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private static Logger logger = LogManager.getLogger(OrderServlet.class);

    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        Facade facade = Facade.getInstance();
        try {
            facade.log(req.getHeader(AUTHORIZATION), this.getClass().getName() + DO_GET);
            Integer idInt = Integer.parseInt(req.getParameter(ID));
            JSONObject object = null;
            object = new JSONObject(facade.getOrderByID(idInt));
            res.getWriter().println(object);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            try {
                facade.log(req.getHeader(AUTHORIZATION), this.getClass().getName() + DO_GET_ERROR + e.getMessage());
            } catch (InternalErrorException e1) {
                logger.log(Level.DEBUG, e1.getMessage());
            }
        }
    }

    public void doPut(HttpServletRequest req, HttpServletResponse res) {
        Facade facade = Facade.getInstance();
        try {
            facade.log(req.getHeader(AUTHORIZATION), this.getClass().getName() + DO_PUT);
            JSONObject object = new JSONObject(JsonEntityParser.parseBuffer(req.getReader()));
            Order order = JsonEntityParser.toOrder(object);
            facade.addOrder(order, null);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            try {
                facade.log(req.getHeader(AUTHORIZATION), this.getClass().getName() + DO_PUT_ERROR + e.getMessage());
            } catch (InternalErrorException e1) {
                logger.log(Level.DEBUG, e1.getMessage());
            }
        }
    }

    public void doDelete(HttpServletRequest req, HttpServletResponse res) {
        Facade facade = Facade.getInstance();
        try {
            facade.log(req.getHeader(AUTHORIZATION), this.getClass().getName() + DO_DELETE);
            Integer idInt = Integer.parseInt(req.getParameter(ID));
            facade.deleteOrder(facade.getOrderByID(idInt));
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            try {
                facade.log(req.getHeader(AUTHORIZATION), this.getClass().getName() + DO_DELETE_ERROR + e.getMessage());
            } catch (InternalErrorException e1) {
                logger.log(Level.DEBUG, e1.getMessage());
            }
        }
    }
}
