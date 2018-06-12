package by.edu.server.beans.notes;

import by.edu.server.beans.IBean;
import by.edu.server.beans.users.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notes")
public class Note implements IBean {
    @Id
    private Integer id;
    @Column
    private String name;
    @Column(length = 100000)
    private String text;
    @Column
    private Date lastEditDate;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner")
    private User owner;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getLastEditDate() {
        return lastEditDate;
    }

    public void setLastEditDate(Date lastEditDate) {
        this.lastEditDate = lastEditDate;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
