package by.edu.server.services.people;

import by.edu.server.beans.people.User;
import by.edu.server.exceptions.IncorrectIDEcxeption;

import javax.transaction.Transactional;

public interface UserService {
    @Transactional
    void addUser(User user);

    @Transactional
    User getUser(String username, String password, boolean toInit);

    @Transactional
    User getUser(Integer id, boolean toInit);

    @Transactional
    void deleteUser(Integer id);

    @Transactional
    User getByPersonId(Integer personId) throws IncorrectIDEcxeption;

    @Transactional
    User updateUser(User user);
}
