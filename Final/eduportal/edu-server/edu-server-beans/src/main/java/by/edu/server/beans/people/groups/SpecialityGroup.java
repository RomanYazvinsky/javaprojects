package by.edu.server.beans.people.groups;

import by.edu.server.beans.IBean;
import by.edu.server.beans.people.Lecturer;
import by.edu.server.beans.people.Student;
import by.edu.server.properties.resources.BeanConstants;

import javax.persistence.*;
import java.util.List;

import static by.edu.server.properties.resources.BeanConstants.BEAN_ID;
@Entity
@Table(name = BeanConstants.TABLE_SPECIALITY_GROUP)
public class SpecialityGroup implements IBean {

    @Id
    @Column(name = BEAN_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = BeanConstants.SPECIALITY_GROUP_NAME)
    private String name;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = BeanConstants.SPECIALITY_GROUP_CURATOR)
    private Lecturer curator;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = BeanConstants.SPECIALITY_GROUP_STUDENTS)
    private List<Student> students;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Lecturer getCurator() {
        return curator;
    }

    public void setCurator(Lecturer curator) {
        this.curator = curator;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
