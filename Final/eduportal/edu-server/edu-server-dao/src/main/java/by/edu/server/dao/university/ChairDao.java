package by.edu.server.dao.university;

import by.edu.server.beans.university.Chair;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChairDao extends JpaRepository<Chair, Integer> {
}
