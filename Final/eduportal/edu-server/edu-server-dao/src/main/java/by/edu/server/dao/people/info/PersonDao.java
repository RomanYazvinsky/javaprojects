package by.edu.server.dao.people.info;

import by.edu.server.beans.enumerations.Role;
import by.edu.server.beans.people.info.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonDao extends JpaRepository<Person, Integer> {
    List<Person> findByRole(Role role);
}
