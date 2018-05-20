package by.edu.server.beans.courses;

import by.edu.server.beans.IBean;
import by.edu.server.beans.people.groups.CourseGroup;
import by.edu.server.properties.resources.BeanConstants;

import javax.persistence.*;
import java.util.List;

import static by.edu.server.properties.resources.BeanConstants.BEAN_ID;

@Entity
@Table(name = BeanConstants.TABLE_COURSE)
public class Course implements IBean {

    @Id
    @Column(name = BEAN_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = BeanConstants.COURSE_COURSE_GROUP)
    private CourseGroup courseGroup;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = BeanConstants.COURSE_SUBJECT_CLASSES)
    private List<SubjectClass> subjectClasses;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CourseGroup getCourseGroup() {
        return courseGroup;
    }

    public void setCourseGroup(CourseGroup courseGroup) {
        this.courseGroup = courseGroup;
    }

    public List<SubjectClass> getSubjectClasses() {
        return subjectClasses;
    }

    public void setSubjectClasses(List<SubjectClass> subjectClasses) {
        this.subjectClasses = subjectClasses;
    }
}
