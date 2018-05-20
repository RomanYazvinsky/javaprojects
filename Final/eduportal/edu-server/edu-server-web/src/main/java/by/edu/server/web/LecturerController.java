package by.edu.server.web;

import by.edu.server.beans.courses.SubjectClass;
import by.edu.server.beans.enumerations.Role;
import by.edu.server.beans.people.User;
import by.edu.server.services.people.LecturerService;
import by.edu.server.services.people.PersonService;
import by.edu.server.services.people.UserService;
import by.edu.server.utils.TokenManager;
import by.edu.server.web.dto.DtoShell;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

//@RestController
@RequestMapping("/lecturer")
public class LecturerController {
    private static Logger logger = LogManager.getLogger(AuthController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private PersonService personService;
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LecturerService lecturerService;

    private User defineUser(String token) {
        return tokenManager.get(token);
    }

    private Boolean checkRights(String token) {
        User user = defineUser(token);
        return user != null && user.getPerson().getRole().equals(Role.LECTURER);
    }

    @RequestMapping(name = "/schedule", method = RequestMethod.GET, produces = "application/json")
    public DtoShell<List<SubjectClass>> getPersons(@RequestHeader(value = "Authorization") String token, @RequestBody DtoShell<List<Date>> request) {
        DtoShell<List<SubjectClass>> response = new DtoShell<>();
        if (!checkRights(token)) {
            response.setMessage("no such user");
            return response;
        }
        try {
            List<Date> dates = request.getData();
            Date from;
            Date to;
            if (dates.size() < 1) {
                return response;
            } else {
                from = dates.get(0);
                if (dates.size() > 1) {
                    to = dates.get(1);
                } else {
                    to = new Date(System.currentTimeMillis());
                }
            }
            response.setData(lecturerService.getSchedule(defineUser(token).getId(), from, to));
            response.setSuccessful(true);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
        return response;
    }
/*
    @RequestMapping(name = "/person", method = RequestMethod.GET)
    public DtoShell<Person> getPerson(@RequestHeader(value = "Authorization") String token, @RequestBody DtoShell<User> userDto) {
        DtoShell<Person> response = new DtoShell<>();
        if (!checkRights(token)) {
            response.setMessage("no such user");
            return response;
        }
        try {
            response.setData(personService.getPerson(userDto.getData().getId(), true));
            response.setSuccessful(true);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
        return response;
    }

    @RequestMapping(name = "/person", method = RequestMethod.POST)
    public DtoShell<Person> editPerson(@RequestHeader(value = "Authorization") String token, @RequestBody DtoShell<User> userDto) {
        DtoShell<Person> response = new DtoShell<>();
        if (!checkRights(token)) {
            response.setMessage("no such user");
            return response;
        }
        try {
            response.setData(personService.getPerson(userDto.getData().getId(), true));
            response.setSuccessful(true);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
        return response;
    }

    @RequestMapping(name = "/register", method = RequestMethod.POST)
    public DtoShell<String> register(@RequestHeader(value = "Authorization") String token, @RequestBody DtoShell<User> userDto) {
        DtoShell<String> response = new DtoShell<>();
        try {
            if (!checkRights(token)) {
                response.setMessage("no such user");
                return response;
            }
            User user = userDto.getData();
            userService.addUser(user);
            response.setSuccessful(true);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
        return response;
    }*/
}
