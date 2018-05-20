package by.edu.server.beans.people.groups;

import by.edu.server.beans.IBean;
import by.edu.server.beans.courses.Course;
import by.edu.server.beans.people.Student;
import by.edu.server.properties.resources.BeanConstants;

import javax.persistence.*;
import java.util.List;

import static by.edu.server.properties.resources.BeanConstants.BEAN_ID;

@Entity
@Table(name = BeanConstants.TABLE_COURSE_GROUP)
public class CourseGroup implements IBean {
    @Id
    @Column(name = BEAN_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = BeanConstants.COURSE_GROUP_NAME)
    private String name;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = BeanConstants.COURSE_GROUP_STUDENTS)
    private List<Student> students;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = BeanConstants.COURSE_GROUP_COURSE)
    private Course course;

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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
