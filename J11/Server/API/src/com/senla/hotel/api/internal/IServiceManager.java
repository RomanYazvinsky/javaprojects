package com.senla.hotel.api.internal;

import com.senla.hotel.constants.SortType;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.*;

import java.util.ArrayList;

public interface IServiceManager {
    ArrayList<Service> sort(SortType sortType) throws QueryFailureException, UnexpectedValueException;

    Boolean add(Service service, boolean addId) throws QueryFailureException, UnexpectedValueException, AnalysisException;

    Integer getPriceForServices(ArrayList<Service> services);

    Service getServiceByID(Integer serviceID) throws AnalysisException, QueryFailureException, UnexpectedValueException;

    ArrayList<Service> getServices() throws QueryFailureException, UnexpectedValueException;

    String[] toStringArray(ArrayList<Service> services);

    ArrayList<Service> importAll() throws EmptyObjectException;

    void updateByImport() throws EmptyObjectException, QueryFailureException, AnalysisException, UnexpectedValueException, DatabaseConnectException;

    void exportAll() throws QueryFailureException, UnexpectedValueException;

    Boolean delete(Service service) throws QueryFailureException, AnalysisException;
}
