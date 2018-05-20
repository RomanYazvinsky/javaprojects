package by.edu.server.beans.courses;

import by.edu.server.beans.IBean;
import by.edu.server.beans.enumerations.SubjectType;
import by.edu.server.beans.university.Chair;
import by.edu.server.properties.resources.BeanConstants;

import javax.persistence.*;

import static by.edu.server.properties.resources.BeanConstants.BEAN_ID;

@Entity
@Table(name = BeanConstants.TABLE_SUBJECT)
public class Subject implements IBean {
    @Id
    @Column(name = BEAN_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = BeanConstants.SUBJECT_NAME)
    private String name;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = BeanConstants.SUBJECT_CHAIR)
    private Chair chair;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = BeanConstants.SUBJECT_TYPE)
    private SubjectType subjectType;

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

    public Chair getChair() {
        return chair;
    }

    public void setChair(Chair chair) {
        this.chair = chair;
    }

    public SubjectType getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(SubjectType subjectType) {
        this.subjectType = subjectType;
    }
}
