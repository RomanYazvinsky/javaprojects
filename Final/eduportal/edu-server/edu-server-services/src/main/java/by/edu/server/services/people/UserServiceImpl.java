package by.edu.server.services.people;

import by.edu.server.beans.people.User;
import by.edu.server.dao.people.UserDao;
import by.edu.server.dao.people.info.PersonDao;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static Logger logger = LogManager.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;
    @Autowired
    private PersonDao personDao;

    @Override
    @Transactional
    public void addUser(User user) {
        try {
            userDao.saveAndFlush(user);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public User getUser(String username, String password, boolean toInit) {
        try {
            List<User> users = userDao.getByUsernameAndPassword(username, password);
            if (users.size() != 1) {
                return null;
            }
            User user = users.get(0);
            if (toInit) {
                user = (User) user.clone();
            }
            return user;
        } catch (CloneNotSupportedException e1) {
            logger.log(Level.DEBUG, e1.getMessage());
            return null;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public User getUser(Integer id, boolean toInit) {
        try {
            User user = userDao.findById(id).get();
            if (toInit) {
                user = (User) user.clone();
            }
            return user;
        } catch (CloneNotSupportedException e1) {
            logger.log(Level.DEBUG, e1.getMessage());
            return null;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void deleteUser(Integer id) {
        try {
            userDao.deleteById(id);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public User getByPersonId(Integer personId) {
        try {
            return userDao.getByPerson(personDao.getOne(personId)).get(0);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        try {
            User toEdit = userDao.getOne(user.getId());
            toEdit.setUsername(user.getUsername());
            toEdit.setPassword(user.getPassword());
            userDao.save(toEdit);
            return toEdit;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }
}
