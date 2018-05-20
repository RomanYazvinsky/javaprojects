package by.edu.server.dao.courses;

import by.edu.server.beans.courses.Course;
import by.edu.server.beans.courses.SubjectClass;
import by.edu.server.beans.people.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface SubjectClassDao extends JpaRepository<SubjectClass, Integer> {
    List<SubjectClass> findAllByLecturerAndBeginningTimeBetween(Lecturer lecturer, Date from, Date to);
    List<SubjectClass> findByCourseAndBeginningTimeBetween(Course course, Date from, Date to);
}
