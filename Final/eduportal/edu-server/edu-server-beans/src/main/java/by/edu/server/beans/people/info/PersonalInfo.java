package by.edu.server.beans.people.info;

import by.edu.server.beans.IBean;

import javax.persistence.*;
import java.util.Date;

import static by.edu.server.properties.resources.BeanConstants.*;

@Entity
@Table(name = TABLE_PERSONAL_INFO)
public class PersonalInfo implements IBean, Cloneable {

    @Id
    @Column(name = BEAN_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = PERSONAL_INFO_NATIONALITY)
    private String nationality;
    @Temporal(TemporalType.DATE)
    @Column(name = PERSONAL_INFO_BIRTH_DATE)
    private Date birthDate;
    @Column(name = PERSONAL_INFO_ADDRESS)
    private String address;
    @Temporal(TemporalType.DATE)
    @Column(name = PERSONAL_INFO_REGISTRATION_DATE)
    private Date registrationDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        PersonalInfo personalInfo = (PersonalInfo) super.clone();
        personalInfo.birthDate = (Date) this.birthDate.clone();
        personalInfo.registrationDate = (Date) this.registrationDate.clone();
        return personalInfo;
    }
}
