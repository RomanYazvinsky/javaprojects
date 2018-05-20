package by.edu.server.dao.courses;

import by.edu.server.beans.courses.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectDao extends JpaRepository<Subject, Integer> {
}
