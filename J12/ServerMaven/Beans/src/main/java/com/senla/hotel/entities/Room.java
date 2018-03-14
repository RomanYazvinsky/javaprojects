package com.senla.hotel.entities;

import com.senla.hotel.annotations.CsvEntity;
import com.senla.hotel.annotations.CsvProperty;
import com.senla.hotel.constants.RoomStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

import static com.senla.hotel.constants.Constants.roomExportFile;
import static com.senla.hotel.constants.Constants.roomHeaderCSV;
import static com.senla.hotel.constants.EntityColumnOrder.*;
import static com.senla.hotel.constants.PropertyType.SIMPLE_PROPERTY;

@Entity
@Table(name = "rooms")
@CsvEntity(csvHeader = roomHeaderCSV, filename = roomExportFile, valueSeparator = ",", entityId = "id")
public class Room implements IEntity, Serializable, Cloneable {
    private static final long serialVersionUID = 6938497574865077752L;

    private static Logger logger = LogManager.getLogger(Room.class);
    @Id()
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @CsvProperty(propertyType = SIMPLE_PROPERTY, columnNumber = ID)
    private Integer id;

    @Column(name = "number")
    @CsvProperty(propertyType = SIMPLE_PROPERTY, columnNumber = ROOM_NUMBER)
    private Integer number;

    @Column(name = "capacity")
    @CsvProperty(propertyType = SIMPLE_PROPERTY, columnNumber = ROOM_CAPACITY)
    private Integer capacity;

    @Column(name = "star")
    @CsvProperty(propertyType = SIMPLE_PROPERTY, columnNumber = ROOM_STAR)
    private Integer star;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @CsvProperty(propertyType = SIMPLE_PROPERTY, columnNumber = ROOM_STATUS)
    private RoomStatus status;

    @Column(name = "room_price")
    @CsvProperty(propertyType = SIMPLE_PROPERTY, columnNumber = ROOM_PRICE)
    private Integer pricePerDay;

    public Room() {

    }

    public Room(int number, int capacity, int star, int pricePerDay) {
        super();
        this.number = number;
        this.capacity = capacity;
        this.star = star;
        this.pricePerDay = pricePerDay;
        this.status = RoomStatus.FREE_NOW;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((number == null) ? 0 : number.hashCode());
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
        Room other = (Room) obj;
        if (id != null && other.id != null) {
            if (id.equals(other.id)) {
                return true;
            }
        }
        if (number == null) {
            if (other.number != null)
                return false;
        } else if (!number.equals(other.number))
            return false;
        return true;
    }

    public Integer getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(int pricePerDay) {
        this.pricePerDay = pricePerDay;

    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;

    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;

    }

    public Boolean isOnService() {
        return status.equals(RoomStatus.ONSERVICE_NOW);
    }

    @Override
    public String toString() {
        return id + ", " + number + ", " + capacity + ", " + star + ", " + status + ", " + pricePerDay;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Room room;
        room = (Room) super.clone();
        room.id = null;
        room.number = null;
        return room;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

}
