package com.senla.hotel.entities;

import com.senla.hotel.annotations.*;

import java.util.Date;
@Table(tableName = "ord_serv")
public class ServiceRecord implements IEntity{
    @Id
    @Column(fieldName = "id")
    private Integer id;
    @Column(fieldName = "order_id")
    @Getter(getter = "getId")
    @ForeignKey(tableName = "orders", internalName = "order_id", externalName = "id")
    private Order order;
    @Column(fieldName = "service_id")
    @Getter(getter = "getId")
    @ForeignKey(tableName = "services", internalName = "service_id", externalName = "id")
    private Service service;
    @Column(fieldName = "date", isString = true)
    @Parseable(methodName = "parseDate")
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
