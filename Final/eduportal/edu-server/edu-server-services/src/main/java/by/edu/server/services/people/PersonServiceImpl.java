package by.edu.server.services.people;

import by.edu.server.beans.enumerations.Role;
import by.edu.server.beans.people.info.Person;
import by.edu.server.dao.people.info.PersonDao;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

public class PersonServiceImpl implements PersonService {
    private static Logger logger = LogManager.getLogger(PersonServiceImpl.class);
    @Autowired
    private PersonDao personDao;

    private Person updatePerson(Person source, Person dest) {
        dest.setName(source.getName());
        dest.setLastName(source.getLastName());
        dest.setSecondName(source.getSecondName());
        dest.setPhotoLink(source.getPhotoLink());
        dest.setPosition(source.getPosition());
        dest.setRole(source.getRole());
        dest.getContacts().setTelephone(source.getContacts().getTelephone());
        dest.getContacts().setAdditional(source.getContacts().getAdditional());
        dest.getContacts().setEmail(source.getContacts().getEmail());
        dest.getPersonalInfo().setNationality(source.getPersonalInfo().getNationality());
        dest.getPersonalInfo().setAddress(source.getPersonalInfo().getAddress());
        dest.getPersonalInfo().setBirthDate(source.getPersonalInfo().getBirthDate());
        dest.getPersonalInfo().setRegistrationDate(source.getPersonalInfo().getRegistrationDate());
        return dest;
    }

    private void init(Person person) {
        if (person == null) {
            return;
        }
        String initContacts = person.getContacts().getTelephone();
        String initPersonalInfo = person.getPersonalInfo().getAddress();
    }

    @Override
    @Transactional
    public List<Person> getAll() {
        List<Person> persons = null;
        try {
            persons = personDao.findAll();
            return persons;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public Person updatePerson(Person person) {
        try {
            Person toEdit = updatePerson(person, personDao.getOne(person.getId()));
            personDao.save(toEdit);
            return toEdit;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public Person getPerson(Integer id, boolean toInit) {
        try {
            Person person = personDao.findById(id).get();
            if (toInit) {
                init(person);
            }
            return person;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public List<Person> getPersonsByRole(Role role) {
        try {
            return personDao.findByRole(role);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }
}
