package by.edu.server.dao.university;

import by.edu.server.beans.university.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyDao extends JpaRepository<Faculty, Integer> {
}
