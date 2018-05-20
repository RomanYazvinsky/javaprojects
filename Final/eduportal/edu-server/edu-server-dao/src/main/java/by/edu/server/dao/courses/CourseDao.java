package by.edu.server.dao.courses;

import by.edu.server.beans.courses.Course;
import by.edu.server.beans.courses.SubjectClass;
import by.edu.server.beans.people.groups.CourseGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseDao extends JpaRepository<Course, Integer> {
    Course findByCourseGroup(CourseGroup courseGroup);
}
