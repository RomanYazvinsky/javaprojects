package by.edu.server.dao.people;

import by.edu.server.beans.people.Lecturer;
import by.edu.server.beans.people.info.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LecturerDao extends JpaRepository<Lecturer, Integer> {
    Lecturer findByPerson(Person person);
}
