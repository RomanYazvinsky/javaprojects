package by.edu.server.services.people;

import by.edu.server.beans.enumerations.Role;
import by.edu.server.beans.people.info.Person;

import javax.transaction.Transactional;
import java.util.List;

public interface PersonService {

    @Transactional
    List<Person> getAll();

    @Transactional
    Person updatePerson(Person person);

    @Transactional
    Person getPerson(Integer id, boolean toInit);

    @Transactional
    List<Person> getPersonsByRole(Role role);
}
