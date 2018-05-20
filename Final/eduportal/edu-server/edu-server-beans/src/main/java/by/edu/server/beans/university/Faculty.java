package by.edu.server.beans.university;

import by.edu.server.beans.IBean;
import by.edu.server.properties.resources.BeanConstants;

import javax.persistence.*;

import java.util.List;

import static by.edu.server.properties.resources.BeanConstants.BEAN_ID;

@Entity
@Table(name = BeanConstants.TABLE_FACULTY)
public class Faculty implements IBean {
    @Id
    @Column(name = BEAN_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = BeanConstants.FACULTY_NAME)
    private String name;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = BeanConstants.FACULTY_CHAIRS)
    private List<Chair> chairs;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Chair> getChairs() {
        return chairs;
    }

    public void setChairs(List<Chair> chairs) {
        this.chairs = chairs;
    }
}
