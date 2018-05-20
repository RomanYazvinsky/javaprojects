package by.edu.server.beans.people;

import by.edu.server.beans.IRole;
import by.edu.server.beans.people.groups.CourseGroup;
import by.edu.server.beans.people.groups.SpecialityGroup;
import by.edu.server.beans.people.info.Person;
import by.edu.server.properties.resources.BeanConstants;

import javax.persistence.*;
import java.util.List;

import static by.edu.server.properties.resources.BeanConstants.BEAN_ID;

@Entity
@Table(name = BeanConstants.TABLE_STUDENT)
public class Student implements IRole {

    @Id
    @Column(name = BEAN_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = BeanConstants.STUDENT_PERSON)
    private Person person;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = BeanConstants.STUDENT_SPECIALITY_GROUP)
    private SpecialityGroup specialityGroup;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = BeanConstants.STUDENT_COURSE_GROUPS)
    private List<CourseGroup> courseGroups;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public SpecialityGroup getSpecialityGroup() {
        return specialityGroup;
    }

    public void setSpecialityGroup(SpecialityGroup specialityGroup) {
        this.specialityGroup = specialityGroup;
    }

    public List<CourseGroup> getCourseGroups() {
        return courseGroups;
    }

    public void setCourseGroups(List<CourseGroup> courseGroups) {
        this.courseGroups = courseGroups;
    }
}
