package by.edu.server.services.people;

import by.edu.server.beans.courses.Course;

import javax.transaction.Transactional;
import java.util.List;

public interface CourseService {
    @Transactional
    void addCourse(Course course);

    List<Course> getAll();

    @Transactional
    Course getCourse(Integer id, boolean toInit);

    @Transactional
    Course getCourseByCourseGroup(Integer courseGroupId, boolean toInit);

    @Transactional
    Course updateCourse(Course course);

    @Transactional
    void deleteCourse(Integer id);
}
