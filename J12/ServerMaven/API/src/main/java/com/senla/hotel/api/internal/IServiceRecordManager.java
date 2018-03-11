package com.senla.hotel.api.internal;

import com.senla.hotel.entities.ServiceRecord;
import com.senla.hotel.exceptions.*;

import java.util.ArrayList;

public interface IServiceRecordManager {
    void add(ServiceRecord serviceRecord) throws QueryFailureException, UnexpectedValueException, AnalysisException, DatabaseConnectException;

    void delete(ServiceRecord serviceRecord) throws QueryFailureException, AnalysisException, DatabaseConnectException;

    void updateByImport() throws EmptyObjectException, QueryFailureException, AnalysisException, UnexpectedValueException, DatabaseConnectException;

    ArrayList<ServiceRecord> importAll() throws EmptyObjectException;

    void exportAll() throws QueryFailureException, UnexpectedValueException, DatabaseConnectException;
}
