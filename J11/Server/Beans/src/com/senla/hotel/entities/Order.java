package com.senla.hotel.entities;

import com.senla.hotel.annotations.*;
import com.senla.hotel.constants.PropertyType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.Date;

import static com.senla.hotel.constants.Constants.orderExportFile;
import static com.senla.hotel.constants.Constants.orderHeaderCSV;
import static com.senla.hotel.constants.EntityColumnOrder.*;

@Table(tableName = "orders")
@CsvEntity(csvHeader = orderHeaderCSV, filename = orderExportFile, valueSeparator = ",", entityId = "id")

public class Order implements Serializable, IEntity {
    private static final long serialVersionUID = -4420394137579611748L;

    private static Logger logger = LogManager.getLogger(Order.class);

    @Id
    @Column(fieldName = "id", isString = false)
    @CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = ID)
    private Integer id;

    @CsvProperty(propertyType = PropertyType.COMPOSITE_PROPERTY, columnNumber = ORDER_ROOM, setterMethod = "setId", getterMethod = "getId")
    @Column(fieldName = "room_id", isString = false)
    @Getter(getter = "getId")
    @ForeignKey(tableName = "rooms", internalName = "room_id", externalName = "id")
    private Room room;

    @CsvProperty(propertyType = PropertyType.COMPOSITE_PROPERTY, columnNumber = ORDER_CLIENT, setterMethod = "setId", getterMethod = "getId")
    @Column(fieldName = "client_id", isString = false)
    @Getter(getter = "getId")
    @ForeignKey(tableName = "clients", internalName = "client_id", externalName = "id")
    private Client client;

    @Column(fieldName = "date_from", isString = true)
    @Parseable(methodName = "parseDate")
    @CsvProperty(propertyType = PropertyType.COMPOSITE_PROPERTY, getterMethod = "getTime", setterMethod = "setTime", columnNumber = ORDER_FROM)
    private Date orderFrom;

    @Column(fieldName = "date_to", isString = true)
    @Parseable(methodName = "parseDate")
    @CsvProperty(propertyType = PropertyType.COMPOSITE_PROPERTY, getterMethod = "getTime", setterMethod = "setTime", columnNumber = ORDER_TO)
    private Date orderTo;


    public Order() {

    }

    public Order(Room room, Client client, Date orderFrom, Date orderTo) {
        super();
        this.room = room;
        this.client = client;
        this.orderFrom = orderFrom;
        this.orderTo = orderTo;
    }

    public Date getOrderTo() {
        return orderTo;
    }

    public void setOrderTo(Date orderTo) {
        this.orderTo = orderTo;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(Date orderFrom) {
        this.orderFrom = orderFrom;
    }


    public Boolean isActive(Date now) {
        if (orderFrom.before(now) && orderTo.after(now)) {
            return true;
        }
        return false;
    }

    public Integer compareDates(Order other) {
        if (orderTo.before(other.getOrderFrom())) {
            return -1;
        }
        if (orderFrom.after(other.getOrderTo())) {
            return 1;
        }
        return 0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((client == null) ? 0 : client.hashCode());
        result = prime * result + ((orderFrom == null) ? 0 : orderFrom.hashCode());
        result = prime * result + ((orderTo == null) ? 0 : orderTo.hashCode());
        result = prime * result + ((room == null) ? 0 : room.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        if (id != null && other.id != null) {
            if (id.equals(other.id)) {
                return true;
            }
        }
        if (client == null) {
            if (other.client != null)
                return false;
        } else if (!client.equals(other.client))
            return false;
        if (compareDates((Order) obj) != 0) {
            return false;
        }
        if (room == null) {
            if (other.room != null)
                return false;
        } else if (!room.equals(other.room))
            return false;
        return true;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(String id) {
        try {
            this.id = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return
                id +
                        ", " + room +
                        ", " + client +
                        ", " + orderFrom +
                        ", " + orderTo +
                        '}';
    }
}
