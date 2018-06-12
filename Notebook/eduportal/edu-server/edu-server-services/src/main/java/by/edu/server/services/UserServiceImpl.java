package by.edu.server.services;

import by.edu.server.beans.notes.Note;
import by.edu.server.beans.users.User;
import by.edu.server.dao.users.UserDao;
import by.edu.server.services.api.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static Logger logger = LogManager.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;

    @Override
    public void addUser(User user) {
        try {
            userDao.saveAndFlush(user);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
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
    public User getUser(Integer id, boolean toInit) {
        try {
            if (!userDao.findById(id).isPresent()){
                return null;
            }
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
    public void deleteUser(Integer id) {
        try {
            userDao.deleteById(id);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public User updateUser(User user, String newHashedPassword) {
        try {
            User toEdit = userDao.getOne(user.getId());
            toEdit.setUsername(user.getUsername());
            if (!toEdit.getPassword().equals(user.getPassword())) {
                toEdit.setPassword(newHashedPassword);
            }
            userDao.save(toEdit);
            return toEdit;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }
}
