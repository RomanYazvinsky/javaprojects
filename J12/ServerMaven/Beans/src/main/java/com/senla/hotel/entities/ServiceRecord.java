package com.senla.hotel.entities;

import com.senla.hotel.annotations.*;
import com.senla.hotel.constants.PropertyType;

import javax.persistence.*;
import java.util.Date;

import static com.senla.hotel.constants.Constants.serviceRecordExportFile;
import static com.senla.hotel.constants.Constants.serviceRecordHeaderCSV;
import static com.senla.hotel.constants.EntityColumnOrder.ID;
import static com.senla.hotel.constants.EntityColumnOrder.SERVICE_DATE;
@Entity
@Table(name = "ord_serv")
@CsvEntity(csvHeader = serviceRecordHeaderCSV, entityId = "id", filename = serviceRecordExportFile, valueSeparator = ",")
public class ServiceRecord implements IEntity{

    @CsvProperty(columnNumber = ID, propertyType = PropertyType.SIMPLE_PROPERTY)
    private Integer id;
    @CsvProperty(propertyType = PropertyType.COMPOSITE_PROPERTY, columnNumber = 2, getterMethod = "getId", setterMethod = "setId")
    private Order order;
    @CsvProperty(propertyType = PropertyType.COMPOSITE_PROPERTY, columnNumber = 3, getterMethod = "getId", setterMethod = "setId")
    private Service service;
    @CsvProperty(propertyType = PropertyType.COMPOSITE_PROPERTY, columnNumber = SERVICE_DATE, getterMethod = "getTime")
    private Date date;

    public ServiceRecord() {
    }

    public ServiceRecord(Integer id, Order order, Service service, Date date) {
        this.id = id;
        this.order = order;
        this.service = service;
        this.date = date;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    public Order getOrder() {
        return order;
    }


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_id")
    public Service getService() {
        return service;
    }


    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
