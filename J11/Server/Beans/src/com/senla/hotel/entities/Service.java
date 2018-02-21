package com.senla.hotel.entities;

import com.senla.hotel.annotations.*;
import com.senla.hotel.constants.PropertyType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.jmx.Server;

import java.io.Serializable;

import static com.senla.hotel.constants.Constants.serviceExportFile;
import static com.senla.hotel.constants.Constants.serviceHeaderCSV;
import static com.senla.hotel.constants.EntityColumnOrder.*;
@Table(tableName = "managers")
@CsvEntity(csvHeader = serviceHeaderCSV, filename = serviceExportFile, valueSeparator = ",", entityId = "id")
public class Service implements IEntity, Serializable {
    private static final long serialVersionUID = 4634029185600413436L;
    private static Logger logger = LogManager.getLogger(Server.class);
    @CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = ID)
    @Id
    @Column(fieldName = "id")
    private Integer id;
    @Column(fieldName = "service_name")
    @CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = NAME)
    private String name;
    @Column(fieldName = "service_price")
    @CsvProperty(propertyType = PropertyType.SIMPLE_PROPERTY, columnNumber = SERVICE_PRICE)
    private Integer price;

    public Service() {

    }

    public Service(int price, String name) {
        super();
        this.price = price;
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(int price) {

        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        {
            this.name = name;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((price == null) ? 0 : price.hashCode());
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
        Service other = (Service) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (id.equals(other.id))
            return true;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (price == null) {
            if (other.price != null)
                return false;
        } else if (!price.equals(other.price))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return id + ", " + price + ", " + name + ", ";
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public void setId(String id) {
        try {
            this.id = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
    }

}
