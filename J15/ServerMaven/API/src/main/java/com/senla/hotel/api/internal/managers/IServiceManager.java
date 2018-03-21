package com.senla.hotel.api.internal.managers;

import com.senla.hotel.constants.SortType;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.*;

import java.util.ArrayList;
import java.util.List;

public interface IServiceManager {
    List<Service> sort(SortType sortType) throws QueryFailureException, UnexpectedValueException, DatabaseConnectException;

    Boolean add(Service service, boolean addId) throws QueryFailureException, UnexpectedValueException, AnalysisException, DatabaseConnectException;

    Integer getPriceForServices(ArrayList<Service> services);

    Service getServiceByID(Integer serviceID) throws AnalysisException, QueryFailureException, UnexpectedValueException, DatabaseConnectException;

    List<Service> getServices() throws QueryFailureException, UnexpectedValueException, DatabaseConnectException;

    String[] toStringArray(ArrayList<Service> services);

    ArrayList<Service> importAll() throws EmptyObjectException;

    void updateByImport() throws EmptyObjectException, QueryFailureException, AnalysisException, UnexpectedValueException, DatabaseConnectException;

    void exportAll() throws QueryFailureException, UnexpectedValueException, DatabaseConnectException;

    Boolean delete(Service service) throws QueryFailureException, AnalysisException, DatabaseConnectException;
}
