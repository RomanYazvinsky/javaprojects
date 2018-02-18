package com.senla.hotel.workers;

import com.senla.hotel.api.internal.IServiceWorker;
import com.senla.hotel.dao.ServiceDao;
import com.senla.hotel.entities.Service;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.utilities.CSVModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.DBConnector;
import utilities.DateCreator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class ServiceWorker implements IServiceWorker {
    private static Logger logger = LogManager.getLogger(ServiceWorker.class);
    private ServiceDao serviceDao;

    public ServiceWorker() {
        serviceDao = new ServiceDao(DBConnector.getInstance().getConnection());
    }

    /* (non-Javadoc)
     * @see com.senla.hotel.workers.IServiceWorker#sort(java.util.ArrayList, java.util.Comparator)
     */
    @Override
    public ArrayList<Service> sort(ArrayList<Service> services, Comparator<Service> comparator) {
        Collections.sort(services, comparator);
        return services;
    }

    /* (non-Javadoc)
     * @see com.senla.hotel.workers.IServiceWorker#add(com.senla.hotel.entities.Service, boolean)
     */
    @Override
    public synchronized Boolean add(Service service, boolean addId) {
        serviceDao.add(service);
        return true;
    }

    /* (non-Javadoc)
     * @see com.senla.hotel.workers.IServiceWorker#getPriceForServices(java.util.ArrayList)
     */
    @Override
    public Integer getPriceForServices(ArrayList<Service> services) {
        if (services == null || services.size() == 0) {
            return 0;
        }
        Integer price = 0;
        for (Service service : services) {
            price += service.getPrice();
        }
        return price;
    }

    /* (non-Javadoc)
     * @see com.senla.hotel.workers.IServiceWorker#getServiceByID(java.lang.Integer)
     */
    @Override
    public Service getServiceByID(Integer serviceID) {
        if (serviceID == null) {
            return null;
        }
        return serviceDao.read(serviceID, Service.class);
    }

    /* (non-Javadoc)
     * @see com.senla.hotel.workers.IServiceWorker#getServices()
     */
    @Override
    public ArrayList<Service> getServices() {
        return serviceDao.getAll();
    }

    /* (non-Javadoc)
     * @see com.senla.hotel.workers.IServiceWorker#toStringArray(java.util.ArrayList)
     */
    @Override
    public String[] toStringArray(ArrayList<Service> services) {
        List<String> result = new ArrayList<>();
        for (Service service : services) {
            result.add(service.toString());
        }
        return result.toArray(new String[result.size()]);
    }

    /* (non-Javadoc)
     * @see com.senla.hotel.workers.IServiceWorker#load(java.lang.String)
     */
    /* (non-Javadoc)
	 * @see com.senla.hotel.workers.IServiceWorker#importAll()
	 */
    @Override
    public synchronized ArrayList<Service> importAll() throws EmptyObjectException {
        ArrayList<Service> clients = new ArrayList<>();
        CSVModule.importAll(Service.class).forEach(new Consumer<Object>() {

            @Override
            public void accept(Object arg0) {
                clients.add((Service) arg0);
            }
        });
        return clients;
    }

    /* (non-Javadoc)
     * @see com.senla.hotel.workers.IServiceWorker#exportAll()
     */
    @Override
    public synchronized void exportAll() {
        CSVModule.exportAll(getServices());
    }

    /* (non-Javadoc)
     * @see com.senla.hotel.workers.IServiceWorker#delete(com.senla.hotel.entities.Service)
     */
    @Override
    public synchronized Boolean delete(Service service) {
        serviceDao.delete(service);
        return true;
    }
}
