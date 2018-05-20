package by.edu.server.services.people;

import by.edu.server.beans.courses.SubjectClass;
import by.edu.server.beans.people.Lecturer;
import by.edu.server.dao.courses.SubjectClassDao;
import by.edu.server.dao.people.LecturerDao;
import by.edu.server.dao.people.info.PersonDao;
import by.edu.server.dao.university.ChairDao;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public class LecturerServiceImpl implements LecturerService {
    private static Logger logger = LogManager.getLogger(LecturerServiceImpl.class);
    @Autowired
    private LecturerDao lecturerDao;
    @Autowired
    private SubjectClassDao subjectClassDao;
    @Autowired
    private PersonDao personDao;
    @Autowired
    private ChairDao chairDao;

    private void init(Lecturer lecturer) {
        if (lecturer == null) {
            return;
        }
        String initContacts = lecturer.getPerson().getContacts().getTelephone();
        String initPersonalInfo = lecturer.getPerson().getPersonalInfo().getAddress();
        String initChair = lecturer.getChair().getFaculty().getName();
    }

    @Override
    @Transactional
    public void addLecturer(Lecturer lecturer) {
        try {
            lecturerDao.saveAndFlush(lecturer);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public List<SubjectClass> getSchedule(Integer id, Date from, Date to) {
        try {
            return subjectClassDao.findAllByLecturerAndBeginningTimeBetween(lecturerDao.getOne(id), from, to);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public Lecturer getLecturer(Integer id, boolean toInit) {
        try {
            Lecturer lecturer = lecturerDao.findById(id).get();
            if (toInit) {
                init(lecturer);
            }
            return lecturer;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public Lecturer getLecturerByPersonId(Integer personId, boolean toInit) {
        try {
            Lecturer lecturer = lecturerDao.findByPerson(personDao.getOne(personId));
            if (toInit) {
                init(lecturer);
            }
            return lecturer;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public Lecturer updateLecturer(Lecturer lecturer) {
        try {
            Lecturer toEdit = lecturerDao.getOne(lecturer.getId());
            toEdit.setChair(chairDao.getOne(lecturer.getChair().getId()));
            lecturerDao.save(toEdit);
            return toEdit;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void deleteLecturer(Integer id) {
        try {
            lecturerDao.deleteById(id);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }
}
