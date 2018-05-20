package by.edu.server.beans.courses;

import by.edu.server.beans.IBean;
import by.edu.server.beans.people.Lecturer;
import by.edu.server.beans.people.groups.CourseGroup;
import by.edu.server.beans.university.Place;
import by.edu.server.properties.resources.BeanConstants;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static by.edu.server.properties.resources.BeanConstants.BEAN_ID;

@Entity
@Table(name = BeanConstants.TABLE_SUBJECT_CLASS)
public class SubjectClass implements IBean {
    @Id
    @Column(name = BEAN_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = BeanConstants.SUBJECT_CLASS_SUBJECT)
    private Subject subject;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = BeanConstants.SUBJECT_CLASS_COURSE)
    private Course course;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = BeanConstants.SUBJECT_CLASS_PLACE)
    private Place place;
    @Column(name = BeanConstants.SUBJECT_CLASS_ADDITIONAL)
    private String additional;
    @Temporal(TemporalType.DATE)
    @Column(name = BeanConstants.SUBJECT_CLASS_BEGINNING_TIME)
    private Date beginningTime;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = BeanConstants.SUBJECT_CLASS_LECTURERS)
    private Lecturer lecturer;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public Date getBeginningTime() {
        return beginningTime;
    }

    public void setBeginningTime(Date beginningTime) {
        this.beginningTime = beginningTime;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
