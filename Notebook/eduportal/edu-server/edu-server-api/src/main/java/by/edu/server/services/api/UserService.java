package by.edu.server.services.api;

import by.edu.server.beans.users.User;
import by.edu.server.exceptions.IncorrectIDEcxeption;

import javax.transaction.Transactional;

public interface UserService {
    void addUser(User user);

    User getUser(String username, String password, boolean toInit);

    User getUser(Integer id, boolean toInit);

    void deleteUser(Integer id);

    User updateUser(User user, String newHashedPassword);
}
