package com.senla.hotel.constants;

public class Constants {
    public static final String PATH_TO_PROPERTIES = "../webapps/hotel.properties";

    public static final String clientExportFile = "Data/Client/clients.parsers";
    public static final String orderExportFile = "Data/Order/orders.parsers";
    public static final String serviceExportFile = "Data/Service/managers.parsers";
    public static final String roomExportFile = "Data/Room/rooms.parsers";
    public static final String serviceRecordExportFile = "Data/ServiceRecords/serviceRecords.parsers";


    public static final String INSTANCE_GETTER = "INSTANCE_GETTER";

    public static final String roomHeaderCSV = "id, number, capacity, star, status, price per day";
    public static final String clientHeaderCSV = "id, name";
    public static final String orderHeaderCSV = "id, room ID, client ID, from, to";
    public static final String serviceHeaderCSV = "id, price, name";
    public static final String serviceRecordHeaderCSV = "id, order id, service id, date";


    public static final String AUTHORIZATION = "Authorization";
    public static final String DO_GET = ".doGet";
    public static final String DO_GET_ERROR = ".doGet ERROR ";
    public static final String DO_PUT = ".doPut";
    public static final String DO_PUT_ERROR = ".doPut ERROR ";
    public static final String DO_DELETE = ".doDelete";
    public static final String DO_DELETE_ERROR = ".doDelete ERROR ";

    public static final String BAD_TOKEN = "bad token";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String BAD_LOGIN_PASSWORD = "bad login/password";

    public static final String NO_SUCH_ACCOUNT = "no such account";
    public static final String TOKEN = "token";
    public static final String REGISTRATION_FAILURE = "Registration failure";
    public static final String LOGOUT = "LOGOUT: ";
    public static final String LOGOUT_FAILURE = "logout failure";


    public static final String APPLICATION_JSON = "application/json";


    public static final String ID = "id";



}
