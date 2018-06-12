package by.edu.server.utils;

import by.edu.server.beans.users.User;

import java.util.Date;

public class ExpirationValidator {
    private User user;
    private Date expiration;

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
