package com.senla.hotel.web.entity.client;

import com.senla.hotel.entities.Client;
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

@WebServlet("/client")
public class ClientServlet extends HttpServlet {
    private static Logger logger = LogManager.getLogger(ClientServlet.class);

    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        Facade facade = Facade.getInstance();
        try {
            facade.log(req.getHeader(AUTHORIZATION), this.getClass().getName() + DO_GET);
            Integer idInt = Integer.parseInt(req.getParameter(ID));
            JSONObject object = null;
            object = new JSONObject(facade.getClientByID(idInt));
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
            Client client = JsonEntityParser.toClient(object);
            facade.addClient(client);
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
            facade.deleteClient(facade.getClientByID(idInt));
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
