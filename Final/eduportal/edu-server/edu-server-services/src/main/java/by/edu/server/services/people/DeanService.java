package by.edu.server.services.people;

import by.edu.server.beans.courses.JournalRecord;
import by.edu.server.beans.courses.Subject;

import javax.transaction.Transactional;
import java.util.List;

public interface DeanService {
    @Transactional
    List<Subject> getAllSubjects();

    @Transactional
    List<JournalRecord> getAllJournalRecords();

    @Transactional
    void addSubject(Subject subject);

    @Transactional
    void addJournalRecord(JournalRecord journalRecord);

    @Transactional
    JournalRecord getJournalRecord(Integer id, boolean toInit);

    @Transactional
    Subject getSubject(Integer id, boolean toInit);

    @Transactional
    Subject updateSubject(Subject subject);

    @Transactional
    void deleteSubject(Integer id);

    @Transactional
    JournalRecord updateJournalRecord(JournalRecord journalRecord);

    @Transactional
    void deleteJournalRecord(Integer id);
}
