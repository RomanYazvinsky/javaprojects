package by.edu.server.services;

import by.edu.server.beans.notes.Note;
import by.edu.server.beans.users.User;
import by.edu.server.dao.notes.NoteDao;
import by.edu.server.services.api.NoteService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {
    private static Logger logger = LogManager.getLogger(NoteServiceImpl.class);
    @Autowired
    private NoteDao noteDao;

    @Override
    public void addNote(Note note) {
        try {
            noteDao.saveAndFlush(note);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public Note getLastOne(User user) {
        try {
            return noteDao.getFirstByOwnerOrderByLastEditDateDesc(user);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Note> getNotesOfUser(User user) {
        try {
            return noteDao.findByOwner(user);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public Note getNote(Integer id) {
        try {
            return noteDao.findByIdJoinOwner(id);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public Note getNote(String name) {
        try {
            return noteDao.findByNameJoinOwner(name);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteNote(Integer id) {
        try {
            noteDao.deleteById(id);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    public Note updateNote(Note note) {
        try {
            Note toEdit = noteDao.getOne(note.getId());
            toEdit.setName(note.getName());
            toEdit.setText(note.getText());
            toEdit.setLastEditDate(note.getLastEditDate());
            noteDao.save(toEdit);
            return toEdit;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }
}
