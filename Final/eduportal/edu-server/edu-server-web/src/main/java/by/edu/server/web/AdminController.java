package by.edu.server.web;

import by.edu.server.beans.enumerations.Role;
import by.edu.server.beans.people.User;
import by.edu.server.beans.people.info.Person;
import by.edu.server.services.people.PersonService;
import by.edu.server.services.people.UserService;
import by.edu.server.utils.TokenManager;
import by.edu.server.web.dto.DtoShell;
import by.edu.server.web.dto.simple.PersonSimpleDto;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@RestController
//@RequestMapping("/admin")
public class AdminController {
    private static Logger logger = LogManager.getLogger(AuthController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private PersonService personService;
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private ModelMapper modelMapper;

    private Boolean checkRights(String token) {
        User user = tokenManager.get(token);
        return user != null && user.getPerson().getRole().equals(Role.ADMIN);
    }

    @RequestMapping(name = "/admin/persons", method = RequestMethod.GET, produces = "application/json")
    public DtoShell<List<PersonSimpleDto>> getPersons(@RequestHeader(value = "Authorization") String token) {
        DtoShell<List<PersonSimpleDto>> response = new DtoShell<>();
        if (!checkRights(token)) {
            response.setMessage("no such user");
            return response;
        }
        try {
            ArrayList<PersonSimpleDto> result = new ArrayList<>();
            modelMapper.map(personService.getAll(), result);
            response.setData(result);
            response.setSuccessful(true);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
        return response;
    }

    @RequestMapping(name = "/admin/person", method = RequestMethod.GET)
    public DtoShell<Person> getPerson(@RequestHeader(value = "Authorization") String token, @RequestBody DtoShell<Integer> personId) {
        DtoShell<Person> response = new DtoShell<>();
        if (!checkRights(token)) {
            response.setMessage("no such user");
            return response;
        }
        try {
            response.setData(personService.getPerson(personId.getData(), true));
            response.setSuccessful(true);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
        return response;
    }

    @RequestMapping(name = "/admin/person", method = RequestMethod.POST)
    public DtoShell<String> editPerson(@RequestHeader(value = "Authorization") String token, @RequestBody DtoShell<PersonSimpleDto> personDto) {
        DtoShell<String> response = new DtoShell<>();
        if (!checkRights(token)) {
            response.setMessage("no such user");
            return response;
        }
        try {
            personService.updatePerson(modelMapper.map(personDto.getData(), Person.class));
            response.setSuccessful(true);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
        return response;
    }

    @RequestMapping(name = "/admin/user", method = RequestMethod.POST)
    public DtoShell<Person> editUser(@RequestHeader(value = "Authorization") String token, @RequestBody DtoShell<User> userDto) {
        DtoShell<Person> response = new DtoShell<>();
        if (!checkRights(token)) {
            response.setMessage("no such user");
            return response;
        }
        try {
            userService.updateUser(userDto.getData());
            response.setSuccessful(true);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
        return response;
    }

    @RequestMapping(name = "/admin/user", method = RequestMethod.GET)
    public DtoShell<User> getUser(@RequestHeader(value = "Authorization") String token, @RequestBody DtoShell<Integer> userId) {
        DtoShell<User> response = new DtoShell<>();
        if (!checkRights(token)) {
            response.setMessage("no such user");
            return response;
        }
        try {
            response.setData(userService.getUser(userId.getData(), true));
            response.setSuccessful(true);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
        return response;
    }

    @RequestMapping(name = "/admin/register", method = RequestMethod.POST)
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
    }
}
