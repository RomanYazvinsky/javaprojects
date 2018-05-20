package by.edu.server.beans.university;

import by.edu.server.beans.IBean;
import by.edu.server.properties.resources.BeanConstants;

import javax.persistence.*;

import static by.edu.server.properties.resources.BeanConstants.BEAN_ID;
import static by.edu.server.properties.resources.BeanConstants.TABLE_PLACE;

@Entity
@Table(name = TABLE_PLACE)
public class Place implements IBean {
    @Id
    @Column(name = BEAN_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = BeanConstants.PLACE_BUILDING)
    private String building;
    @Column(name = BeanConstants.PLACE_CABINET)
    private String cabinet;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getCabinet() {
        return cabinet;
    }

    public void setCabinet(String cabinet) {
        this.cabinet = cabinet;
    }
}
