package com.senla.hotel.entities;

import com.senla.hotel.annotations.*;
import com.senla.hotel.constants.PropertyType;

import java.util.Date;

import static com.senla.hotel.constants.Constants.serviceRecordExportFile;
import static com.senla.hotel.constants.Constants.serviceRecordHeaderCSV;
import static com.senla.hotel.constants.EntityColumnOrder.ID;
import static com.senla.hotel.constants.EntityColumnOrder.SERVICE_DATE;

@Table(tableName = "ord_serv")
@CsvEntity(csvHeader = serviceRecordHeaderCSV, entityId = "id", filename = serviceRecordExportFile, valueSeparator = ",")
public class ServiceRecord implements IEntity{
    @Id
    @Column(fieldName = "id")
    @CsvProperty(columnNumber = ID, propertyType = PropertyType.SIMPLE_PROPERTY)
    private Integer id;
    @Column(fieldName = "order_id")
    @Getter(getter = "getId")
    @CsvProperty(propertyType = PropertyType.COMPOSITE_PROPERTY, columnNumber = 2, getterMethod = "getId", setterMethod = "setId")
    @ForeignKey(tableName = "orders", internalName = "order_id", externalName = "id")
    private Order order;
    @Column(fieldName = "service_id")
    @Getter(getter = "getId")
    @CsvProperty(propertyType = PropertyType.COMPOSITE_PROPERTY, columnNumber = 3, getterMethod = "getId", setterMethod = "setId")
    @ForeignKey(tableName = "managers", internalName = "service_id", externalName = "id")
    private Service service;
    @Column(fieldName = "date", isString = true)
    @Parseable(methodName = "parseDate")
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

    public Order getOrder() {
        return order;
    }

    public Service getService() {
        return service;
    }

    public Date getDate() {
        return date;
    }

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
