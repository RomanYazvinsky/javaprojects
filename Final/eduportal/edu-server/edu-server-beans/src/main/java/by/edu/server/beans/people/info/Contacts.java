package by.edu.server.beans.people.info;

import by.edu.server.beans.IBean;

import javax.persistence.*;

import static by.edu.server.properties.resources.BeanConstants.*;

@Entity
@Table(name = TABLE_CONTACTS, uniqueConstraints = {@UniqueConstraint(name = CONTACTS_EMAIL, columnNames = CONTACTS_EMAIL)})
public class Contacts implements IBean, Cloneable {
    @Id
    @Column(name = BEAN_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = CONTACTS_PHONE)
    private String telephone;
    @Column(name = CONTACTS_EMAIL)
    private String email;
    @Column(name = CONTACTS_ADDITIONAL)
    private String additional;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
