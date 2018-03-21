package com.senla.hotel.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "actionlogs")
public class ActionLog implements IEntity, Serializable {
    private static final long serialVersionUID = 1676886118386345127L;
    private static Logger logger = LogManager.getLogger(ActionLog.class);
    @Id()
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;
    @Column(name = "user")
    private User user;
    private String message;
    @Column(name = "moment")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public ActionLog() {

    }
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActionLog)) return false;
        ActionLog actionLog = (ActionLog) o;
        return Objects.equals(id, actionLog.id) &&
                Objects.equals(user, actionLog.user) &&
                Objects.equals(message, actionLog.message) &&
                Objects.equals(date, actionLog.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, user, message, date);
    }
}
