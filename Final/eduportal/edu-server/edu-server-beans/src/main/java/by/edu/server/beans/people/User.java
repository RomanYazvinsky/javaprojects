package by.edu.server.beans.people;

import by.edu.server.beans.IBean;
import by.edu.server.beans.people.info.Person;

import javax.persistence.*;

import java.util.Objects;

import static by.edu.server.properties.resources.BeanConstants.*;

@Entity
@Table(name = TABLE_USER, uniqueConstraints = {
        @UniqueConstraint(name = USER_USERNAME, columnNames = USER_USERNAME)
})
public class User implements IBean, Cloneable {
    @Id
    @Column(name = BEAN_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = USER_USERNAME, nullable = false)
    private String username;
    @Column(name = USER_PASSWORD, nullable = false)
    private String password;
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = USER_PERSON, nullable = false)
    private Person person;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(username, password);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        User user = (User) super.clone();
        if (this.person!=null){
            user.person = (Person) this.person.clone();
        }
        return user;
    }
}
