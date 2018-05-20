package by.edu.server.dao.people.groups;

import by.edu.server.beans.people.groups.CourseGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseGroupDao extends JpaRepository<CourseGroup, Integer> {
}
