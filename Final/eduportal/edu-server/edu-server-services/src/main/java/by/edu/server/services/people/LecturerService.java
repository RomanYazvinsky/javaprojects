package by.edu.server.services.people;

import by.edu.server.beans.courses.SubjectClass;
import by.edu.server.beans.people.Lecturer;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface LecturerService {
    @Transactional
    void addLecturer(Lecturer lecturer);

    @Transactional
    List<SubjectClass> getSchedule(Integer id, Date from, Date to);

    @Transactional
    Lecturer getLecturer(Integer id, boolean toInit);

    @Transactional
    Lecturer getLecturerByPersonId(Integer personId, boolean toInit);

    @Transactional
    Lecturer updateLecturer(Lecturer lecturer);

    @Transactional
    void deleteLecturer(Integer id);
}
