package by.edu.server.beans.people.info;

import by.edu.server.beans.IBean;
import by.edu.server.beans.enumerations.Role;

import javax.persistence.*;

import static by.edu.server.properties.resources.BeanConstants.*;

@Entity
@Table(name = TABLE_PERSON, uniqueConstraints = {@UniqueConstraint(name = PERSON_PHOTO, columnNames = PERSON_PHOTO)})
public class Person implements IBean, Cloneable {
    @Id
    @Column(name = BEAN_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = PERSON_NAME, nullable = false)
    private String name;
    @Column(name = PERSON_LAST_NAME, nullable = false)
    private String lastName;
    @Column(name = PERSON_SECOND_NAME)
    private String secondName;
    @Column(name = PERSON_PHOTO)
    private String photoLink;
    @Column(name = PERSON_ROLE, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = PERSON_POSITION)
    private String position;
    @JoinColumn(name = PERSON_PERSONAL_INFO)
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private PersonalInfo personalInfo;
    @JoinColumn(name = PERSON_CONTACTS)
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private Contacts contacts;

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Person person = (Person) super.clone();
        if (this.contacts != null) {
            person.contacts = (Contacts) this.contacts.clone();
        }
        if (this.personalInfo!=null){
            person.personalInfo = (PersonalInfo) this.personalInfo.clone();
        }
        return person;
    }
}
