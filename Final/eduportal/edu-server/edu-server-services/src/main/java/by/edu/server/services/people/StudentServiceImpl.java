package by.edu.server.services.people;

import by.edu.server.beans.courses.SubjectClass;
import by.edu.server.beans.people.Student;
import by.edu.server.beans.people.groups.CourseGroup;
import by.edu.server.dao.courses.SubjectClassDao;
import by.edu.server.dao.people.StudentDao;
import by.edu.server.dao.people.info.PersonDao;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentServiceImpl implements StudentService {
    private static Logger logger = LogManager.getLogger(StudentServiceImpl.class);
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private SubjectClassDao subjectClassDao;
    @Autowired
    private PersonDao personDao;

    private void init(Student student) {
        if (student == null) {
            return;
        }
        String initContacts = student.getPerson().getContacts().getTelephone();
        String initPersonalInfo = student.getPerson().getPersonalInfo().getAddress();
        String initSpecialityGroup = student.getSpecialityGroup().getName();
        List<CourseGroup> initCourseGroups = student.getCourseGroups();
    }

    @Override
    @Transactional
    public void addStudent(Student student) {
        try {
            studentDao.saveAndFlush(student);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public List<SubjectClass> getSchedule(Integer id, Date from, Date to) {
        try {
            Student student = studentDao.getOne(id);
            List<CourseGroup> courseGroups = student.getCourseGroups();
            List<SubjectClass> schedule = new ArrayList<>();
            for (CourseGroup courseGroup : courseGroups) {
                schedule.addAll(subjectClassDao.findByCourseAndBeginningTimeBetween(courseGroup.getCourse(), from, to));
            }
            return schedule;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public Student getStudent(Integer id, boolean toInit) {
        try {
            Student student = studentDao.findById(id).get();
            if (toInit) {
                init(student);
            }
            return student;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public Student getStudentByPersonId(Integer personId, boolean toInit) {
        try {
            Student student = studentDao.findByPerson(personDao.getOne(personId));
            if (toInit) {
                init(student);
            }
            return student;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public Student updateStudent(Student student) {
        try {
            Student toEdit = studentDao.getOne(student.getId());
            toEdit.setSpecialityGroup(student.getSpecialityGroup());
            toEdit.getCourseGroups().clear();
            toEdit.getCourseGroups().addAll(student.getCourseGroups());
            studentDao.save(toEdit);
            return toEdit;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void deleteStudent(Integer id) {
        try {
            studentDao.deleteById(id);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }
}
