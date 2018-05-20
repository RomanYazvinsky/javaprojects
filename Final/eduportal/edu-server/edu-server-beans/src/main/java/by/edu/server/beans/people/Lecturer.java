package by.edu.server.beans.people;

import by.edu.server.beans.IRole;
import by.edu.server.beans.courses.SubjectClass;
import by.edu.server.beans.people.info.Person;
import by.edu.server.beans.university.Chair;
import by.edu.server.properties.resources.BeanConstants;

import javax.persistence.*;
import java.util.List;

import static by.edu.server.properties.resources.BeanConstants.BEAN_ID;

@Entity
@Table(name = BeanConstants.TABLE_LECTURER)
public class Lecturer implements IRole {

    @Id
    @Column(name = BEAN_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = BeanConstants.LECTURER_PERSON)
    private Person person;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = BeanConstants.LECTURER_CHAIR)
    private Chair chair;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = BeanConstants.LECTURER_CLASSES)
    private List<SubjectClass> subjectClasses;

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

    public Chair getChair() {
        return chair;
    }

    public void setChair(Chair chair) {
        this.chair = chair;
    }

    public List<SubjectClass> getSubjectClasses() {
        return subjectClasses;
    }

    public void setSubjectClasses(List<SubjectClass> subjectClasses) {
        this.subjectClasses = subjectClasses;
    }
}
