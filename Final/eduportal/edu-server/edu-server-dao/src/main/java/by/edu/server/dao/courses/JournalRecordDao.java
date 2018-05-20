package by.edu.server.dao.courses;

import by.edu.server.beans.courses.JournalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalRecordDao extends JpaRepository<JournalRecord, Integer> {
}
