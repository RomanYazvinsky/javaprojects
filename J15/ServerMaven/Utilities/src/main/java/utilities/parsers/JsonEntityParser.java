package utilities.parsers;

import com.senla.hotel.constants.RoomStatus;
import com.senla.hotel.entities.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

public class JsonEntityParser {
    private static Logger logger = LogManager.getLogger(JsonEntityParser.class);

    public static String parseBuffer(BufferedReader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }

    }

    public static Client toClient(JSONObject object) {
        Client client = new Client();
        try {
            client.setId(object.getInt("id"));
            client.setName(object.getString("name"));
            return client;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    public static Room toRoom(JSONObject object) {
        Room room = new Room();
        try {
            room.setId(object.getInt("id"));
            room.setCapacity(object.getInt("capacity"));
            room.setNumber(object.getInt("number"));
            room.setStatus(RoomStatus.valueOf(object.getString("status")));
            room.setPricePerDay(object.getInt("price"));
            room.setStar(object.getInt("star"));
            return room;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    public static Order toOrder(JSONObject object) {
        Order order = new Order();
        Room room;
        Client client;
        try {
            order.setId(object.getInt("id"));
            room = toRoom(object.getJSONObject("room"));
            client = toClient(object.getJSONObject("client"));
            order.setClient(client);
            order.setRoom(room);
            return order;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    public static Service toService(JSONObject object) {
        Service service = new Service();
        try {
            service.setId(object.getInt("id"));
            service.setName(object.getString("name"));
            service.setPrice(object.getInt("price"));
            return service;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    public static ServiceRecord toServiceRecord(JSONObject object) {
        ServiceRecord serviceRecord = new ServiceRecord();
        Service service;
        Order order;
        try {
            serviceRecord.setId(object.getInt("id"));
            service = toService(object.getJSONObject("room"));
            order = toOrder(object.getJSONObject("client"));
            serviceRecord.setOrder(order);
            serviceRecord.setService(service);
            return serviceRecord;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }
}
