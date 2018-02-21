package com.senla.hotel.api.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.AnalysisException;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.exceptions.QueryFailureException;
import com.senla.hotel.exceptions.UnexpectedValueException;

public interface IServiceWorker {

	Boolean add(Service service, boolean addId) throws QueryFailureException, UnexpectedValueException, AnalysisException;

	Integer getPriceForServices(ArrayList<Service> services);

	Service getServiceByID(Integer serviceID) throws AnalysisException, QueryFailureException, UnexpectedValueException;

	ArrayList<Service> getServices() throws QueryFailureException, UnexpectedValueException;

	String[] toStringArray(ArrayList<Service> services);

	ArrayList<Service> importAll() throws EmptyObjectException;

	void exportAll() throws QueryFailureException, UnexpectedValueException;

	Boolean delete(Service service) throws QueryFailureException, AnalysisException;

}