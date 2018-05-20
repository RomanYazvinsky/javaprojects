package by.edu.server.dao.people;

import by.edu.server.beans.people.Student;
import by.edu.server.beans.people.info.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentDao extends JpaRepository<Student, Integer> {
    Student findByPerson(Person person);
}
