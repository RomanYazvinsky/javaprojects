package com.senla.hotel.web.entity.servicerecords;

import com.senla.hotel.entities.ServiceRecord;
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
import java.io.IOException;

import static com.senla.hotel.constants.Constants.*;

@WebServlet("/record")
public class ServiceRecordServlet extends HttpServlet {
    private static Logger logger = LogManager.getLogger(ServiceRecordServlet.class);

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        Facade facade = Facade.getInstance();
        try {
            facade.log(req.getHeader(AUTHORIZATION), this.getClass().getName() + DO_GET);
            Integer idInt = Integer.parseInt(req.getParameter(ID));
            JSONObject object = null;
            object = new JSONObject(facade.getServiceRecordByID(idInt));
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

    public void doPut(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        Facade facade = Facade.getInstance();
        try {
            facade.log(req.getHeader(AUTHORIZATION), this.getClass().getName() + DO_PUT);
            JSONObject object = new JSONObject(JsonEntityParser.parseBuffer(req.getReader()));
            ServiceRecord serviceRecord = JsonEntityParser.toServiceRecord(object);
            facade.addServiceRecord(serviceRecord);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            try {
                facade.log(req.getHeader(AUTHORIZATION), this.getClass().getName() + DO_PUT_ERROR + e.getMessage());
            } catch (InternalErrorException e1) {
                logger.log(Level.DEBUG, e1.getMessage());
            }
        }
    }

    public void doDelete(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        Facade facade = Facade.getInstance();
        try {
            facade.log(req.getHeader(AUTHORIZATION), this.getClass().getName() + DO_DELETE);
            Integer idInt = Integer.parseInt(req.getParameter(ID));
            facade.deleteServiceRecord(facade.getServiceRecordByID(idInt));
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
