package by.edu.server.dao.people.info;

import by.edu.server.beans.people.info.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactsDao extends JpaRepository<Contacts, Integer> {
}
