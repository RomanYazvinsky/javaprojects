package by.edu.server.services.people;

import by.edu.server.beans.courses.Course;
import by.edu.server.beans.courses.SubjectClass;
import by.edu.server.dao.courses.CourseDao;
import by.edu.server.dao.people.groups.CourseGroupDao;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

public class CourseServiceImpl implements CourseService {
    private static Logger logger = LogManager.getLogger(CourseServiceImpl.class);
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CourseGroupDao courseGroupDao;

    private void init(Course course) {
        String initGroup = course.getCourseGroup().getName();
        List<SubjectClass> initClasses = course.getSubjectClasses();
    }

    @Override
    @Transactional
    public void addCourse(Course course) {
        try {
            courseDao.saveAndFlush(course);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public List<Course> getAll() {
        try {
            return courseDao.findAll();
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public Course getCourse(Integer id, boolean toInit) {
        try {
            Course course = courseDao.findById(id).get();
            if (toInit) {
                init(course);
            }
            return course;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public Course getCourseByCourseGroup(Integer courseGroupId, boolean toInit) {
        try {
            Course course = courseDao.findByCourseGroup(courseGroupDao.getOne(courseGroupId));
            if (toInit) {
                init(course);
            }
            return course;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public Course updateCourse(Course course) {
        try {
            Course toEdit = courseDao.getOne(course.getId());
            toEdit.setSubjectClasses(course.getSubjectClasses());
            courseDao.save(toEdit);
            return toEdit;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void deleteCourse(Integer id) {
        try {
            courseDao.deleteById(id);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }
}
