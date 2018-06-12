package by.edu.server.web;

import by.edu.server.beans.enumerations.Role;
import by.edu.server.beans.notes.Note;
import by.edu.server.beans.users.User;
import by.edu.server.properties.resources.WebConstants;
import by.edu.server.services.api.NoteService;
import by.edu.server.services.api.UserService;
import by.edu.server.utils.api.TokenManager;
import by.edu.server.web.dto.DtoShell;
import by.edu.server.web.dto.simple.NoteSimpleDto;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private static Logger logger = LogManager.getLogger(UserController.class);
    @Autowired
    private NoteService noteService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TokenManager tokenManager;
    @Value("${validation-fail}")
    private String validationFailed;

    private User checkRights(String token) {
        User user = tokenManager.get(token);
        return user.getRole().equals(Role.USER) ? user : null;
    }

    @RequestMapping(value = "/notes", method = RequestMethod.POST, produces = WebConstants.APPLICATION)
    public DtoShell<Boolean> addNote(@RequestHeader(value = WebConstants.AUTHORIZATION) String token, @RequestBody DtoShell<Note> noteDtoShell) {
        DtoShell<Boolean> response = new DtoShell<>();
        User user = checkRights(token);
        if (user == null) {
            response.setMessage(validationFailed);
            return response;
        }
        try {
            Note note = noteDtoShell.getData();
            note.setOwner(user);
            noteService.addNote(note);
            response.setData(true);
            response.setSuccessful(true);

        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/notes", method = RequestMethod.GET, produces = WebConstants.APPLICATION)
    public DtoShell<List<NoteSimpleDto>> getNotesInfo(@RequestHeader(value = WebConstants.AUTHORIZATION) String token, HttpServletRequest request) {
        DtoShell<List<NoteSimpleDto>> response = new DtoShell<>();
        User user = checkRights(token);
        if (user == null) {
            response.setMessage(validationFailed);
            return response;
        }
        try {
            List<Note> notes = noteService.getNotesOfUser(user);
            List<NoteSimpleDto> noteSimpleDtos = new ArrayList<>();
            modelMapper.map(notes, noteSimpleDtos);
            response.setData(noteSimpleDtos);
            response.setSuccessful(true);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/notes/{noteName}", method = RequestMethod.GET, produces = WebConstants.APPLICATION)
    public DtoShell<Note> getNote(@RequestHeader(value = WebConstants.AUTHORIZATION) String token, @PathVariable String noteName) {
        DtoShell<Note> response = new DtoShell<>();
        User user = checkRights(token);
        if (user == null) {
            response.setMessage(validationFailed);
            return response;
        }
        try {
            Note note = noteService.getNote(noteName);
            if (note.getOwner().getId().equals(user.getId())) {
                response.setData(note);
                response.setSuccessful(true);
            }
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/note", method = RequestMethod.GET, produces = WebConstants.APPLICATION)
    public DtoShell<Note> getLastNote(@RequestHeader(value = WebConstants.AUTHORIZATION) String token, HttpServletRequest request) {
        DtoShell<Note> response = new DtoShell<>();
        User user = checkRights(token);
        if (user == null) {
            response.setMessage(validationFailed);
            return response;
        }
        try {
            Note note = noteService.getLastOne(user);
            response.setData(note);
            response.setSuccessful(true);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/note", method = RequestMethod.POST, produces = WebConstants.APPLICATION)
    public DtoShell<Boolean> updateNote(@RequestHeader(value = WebConstants.AUTHORIZATION) String token, @RequestBody DtoShell<Note> note) {
        DtoShell<Boolean> response = new DtoShell<>();
        User user = checkRights(token);
        if (user == null) {
            response.setMessage(validationFailed);
            return response;
        }
        try {
            // TODO: check if this user is owner of incoming note
            noteService.updateNote(note.getData());
            response.setData(true);
            response.setSuccessful(true);

        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
        return response;
    }
}
