package by.edu.server.dao.people.info;

import by.edu.server.beans.people.info.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalInfoDao extends JpaRepository<PersonalInfo, Integer> {
}
