package by.edu.server.beans.courses;

import by.edu.server.beans.IBean;
import by.edu.server.beans.people.Student;
import by.edu.server.properties.resources.BeanConstants;

import javax.persistence.*;

import static by.edu.server.properties.resources.BeanConstants.BEAN_ID;

@Entity
@Table(name = BeanConstants.TABLE_JOURNAL_RECORD)
public class JournalRecord implements IBean {
    @Id
    @Column(name = BEAN_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = BeanConstants.JOURNAL_RECORD_SUBJECT_CLASS)
    private SubjectClass subjectClass;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = BeanConstants.JOURNAL_RECORD_STUDENT)
    private Student student;
    @Column(name = BeanConstants.JOURNAL_RECORD_MARK)
    private Integer mark;
    @Column(name = BeanConstants.JOURNAL_RECORD_ABSENCE)
    private Boolean absence;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SubjectClass getSubjectClass() {
        return subjectClass;
    }

    public void setSubjectClass(SubjectClass subjectClass) {
        this.subjectClass = subjectClass;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Boolean getAbsence() {
        return absence;
    }

    public void setAbsence(Boolean absence) {
        this.absence = absence;
    }
}
