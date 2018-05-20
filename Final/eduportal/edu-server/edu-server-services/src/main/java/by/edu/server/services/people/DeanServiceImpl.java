package by.edu.server.services.people;

import by.edu.server.beans.courses.JournalRecord;
import by.edu.server.beans.courses.Subject;
import by.edu.server.dao.courses.JournalRecordDao;
import by.edu.server.dao.courses.SubjectDao;
import by.edu.server.dao.people.StudentDao;
import by.edu.server.dao.university.ChairDao;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

public class DeanServiceImpl implements DeanService {
    private static Logger logger = LogManager.getLogger(DeanServiceImpl.class);
    @Autowired
    private SubjectDao subjectDao;
    @Autowired
    private JournalRecordDao journalRecordDao;
    @Autowired
    private ChairDao chairDao;
    @Autowired
    private StudentDao studentDao;


    private void initJournalRecord(JournalRecord journalRecord) {
        if (journalRecord == null) {
            return;
        }
        Integer studentId = journalRecord.getStudent().getId();
        Integer subjectClassId = journalRecord.getSubjectClass().getId();
    }

    private void initSubject(Subject subject) {
        if (subject == null) {
            return;
        }
        Integer chairId = subject.getChair().getId();
    }

    @Override
    @Transactional
    public List<Subject> getAllSubjects() {
        try {
            return subjectDao.findAll();
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public List<JournalRecord> getAllJournalRecords() {
        try {
            return journalRecordDao.findAll();
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void addSubject(Subject subject) {
        try {
            subjectDao.saveAndFlush(subject);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }


    @Override
    @Transactional
    public void addJournalRecord(JournalRecord journalRecord) {
        try {
            journalRecordDao.saveAndFlush(journalRecord);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public JournalRecord getJournalRecord(Integer id, boolean toInit) {
        try {
            JournalRecord journalRecord = journalRecordDao.findById(id).get();
            if (toInit) {
                initJournalRecord(journalRecord);
            }
            return journalRecord;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public Subject getSubject(Integer id, boolean toInit) {
        try {
            Subject subject = subjectDao.findById(id).get();
            if (toInit) {
                initSubject(subject);
            }
            return subject;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public Subject updateSubject(Subject subject) {
        try {
            Subject toEdit = subjectDao.getOne(subject.getId());
            toEdit.setName(subject.getName());
            toEdit.setSubjectType(subject.getSubjectType());
            if (!toEdit.getChair().getId().equals(subject.getChair().getId())) {
                toEdit.setChair(chairDao.getOne(subject.getChair().getId()));
            }
            subjectDao.save(toEdit);
            return toEdit;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void deleteSubject(Integer id) {
        try {
            subjectDao.deleteById(id);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public JournalRecord updateJournalRecord(JournalRecord journalRecord) {
        try {
            JournalRecord toEdit = journalRecordDao.getOne(journalRecord.getId());
            toEdit.setAbsence(journalRecord.getAbsence());
            toEdit.setMark(journalRecord.getMark());
            toEdit.setSubjectClass(journalRecord.getSubjectClass());
            if (!toEdit.getStudent().getId().equals(journalRecord.getStudent().getId())) {
                toEdit.setStudent(studentDao.getOne(journalRecord.getStudent().getId()));
            }
            journalRecordDao.save(toEdit);
            return toEdit;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void deleteJournalRecord(Integer id) {
        try {
            journalRecordDao.deleteById(id);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }
}
