package by.edu.server.dao.people;

import by.edu.server.beans.people.User;
import by.edu.server.beans.people.info.Person;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {
    List<User> getByUsernameAndPassword(String username, String password);

    List<User> getByPerson(Person person);
}
