package com.senla.hotel.api.internal;

import com.senla.hotel.constants.SortType;
import com.senla.hotel.entities.ServiceRecord;
import com.senla.hotel.exceptions.AnalysisException;
import com.senla.hotel.exceptions.QueryFailureException;
import com.senla.hotel.exceptions.UnexpectedValueException;

import java.util.ArrayList;

public interface IServiceRecordDao<T> extends IGenericDao<T> {
    boolean add(ServiceRecord serviceRecord) throws QueryFailureException, UnexpectedValueException, AnalysisException;

    ArrayList<ServiceRecord> getAll() throws QueryFailureException, UnexpectedValueException;

    ArrayList<ServiceRecord> getAll(SortType sortType) throws QueryFailureException, UnexpectedValueException;
}
