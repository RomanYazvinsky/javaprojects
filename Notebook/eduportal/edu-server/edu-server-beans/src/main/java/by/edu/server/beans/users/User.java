package by.edu.server.beans.users;

import by.edu.server.beans.IBean;
import by.edu.server.beans.enumerations.Role;
import by.edu.server.beans.notes.Note;

import javax.persistence.*;
import java.util.List;
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
    @Column(nullable = false)
    private String password;
    @Column(name = PERSON_PHOTO)
    private String photoLink;
    @Column(name = PERSON_ROLE, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "notes")
    private List<Note> notes;

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
        return user;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
