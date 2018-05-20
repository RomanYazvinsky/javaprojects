package by.edu.server.web;

import by.edu.server.beans.people.User;
import by.edu.server.services.people.UserService;
import by.edu.server.utils.TokenManager;
import by.edu.server.web.dto.DtoShell;
import by.edu.server.web.dto.simple.UserSimpleDto;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthController {
    private static Logger logger = LogManager.getLogger(AuthController.class);
    @Autowired
    private UserService userService;
    @Value("${self-registration}")
    private String selfRegistration;
    @Autowired
    private TokenManager tokenManager;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public DtoShell<User> logIn(@RequestBody DtoShell<UserSimpleDto> loginDto) {
        UserSimpleDto request = loginDto.getData();
        DtoShell<User> response = new DtoShell<>();
        User user = null;
        String token = null;
        try {
            user = userService.getUser(request.getUsername(), request.getPassword(), true);
            token = tokenManager.add(user);
            response.setData(user);
            response.setSuccessful(true);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
        response.setMessage(token);
        return response;
    }
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public DtoShell<String> register(@RequestBody DtoShell<User> userDto) {
        DtoShell<String> response = new DtoShell<>();
        try {
            Boolean isSelfRegistrationAllowed = Boolean.getBoolean(selfRegistration);
            if (!isSelfRegistrationAllowed) {
                response.setMessage("not allowed");
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

    @RequestMapping(value = "/register", method = RequestMethod.GET, produces = "application/json")
    public DtoShell<Boolean> checkSelfRegistration(HttpServletRequest request) {
        DtoShell<Boolean> response = new DtoShell<>();
        try {
            response.setData(Boolean.valueOf(selfRegistration));
            response.setSuccessful(true);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST,  produces = "application/json")
    public DtoShell<String> logOut(@RequestHeader(value = "Authorization") String token, HttpServletRequest request) {
        DtoShell<String> response = new DtoShell<>();
        try {
            tokenManager.delete(token);
            response.setSuccessful(true);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
        return response;
    }
    @RequestMapping(value = "test", method = RequestMethod.GET, produces = "application/json")
    public DtoShell<String> test(){
        DtoShell<String> stringDtoShell = new DtoShell<>();
        stringDtoShell.setData("test");
        return stringDtoShell;
    }
}

