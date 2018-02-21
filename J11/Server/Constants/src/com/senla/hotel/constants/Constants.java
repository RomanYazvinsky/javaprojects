package com.senla.hotel.constants;

import java.io.IOException;
import java.util.logging.FileHandler;


public class Constants {
    public static final String PATH_TO_PROPERTIES = "../Properties/hotel.properties";
    public static final String PATH_TO_DATABASE_PROPERTIES = "../DBProperties/db.properties";

    public static final String clientExportFile = "Data/Client/clients.parsers";
    public static final String orderExportFile = "Data/Order/orders.parsers";
    public static final String serviceExportFile = "Data/Service/managers.parsers";
    public static final String roomExportFile = "Data/Room/rooms.parsers";
    public static final String serviceRecordExportFile = "Data/ServiceRecords/serviceRecords.parsers";


    public static final String INSTANCE_GETTER = "INSTANCE_GETTER";
    public static final String extensionCSV = ".parsers";

    public static final String roomHeaderCSV = "id, number, capacity, star, status, price per day";
    public static final String clientHeaderCSV = "id, name";
    public static final String orderHeaderCSV = "id, room ID, client ID, from, to";
    public static final String serviceHeaderCSV = "id, price, name";
    public static final String serviceRecordHeaderCSV = "id, order id, service id, date";

}
