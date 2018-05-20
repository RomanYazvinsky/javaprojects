package by.edu.server.services.people;

import by.edu.server.beans.courses.SubjectClass;
import by.edu.server.beans.people.Student;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface StudentService {
    @Transactional
    void addStudent(Student student);

    @Transactional
    List<SubjectClass> getSchedule(Integer id, Date from, Date to);

    @Transactional
    Student getStudent(Integer id, boolean toInit);

    @Transactional
    Student getStudentByPersonId(Integer personId, boolean toInit);

    @Transactional
    Student updateStudent(Student student);

    @Transactional
    void deleteStudent(Integer id);
}
