package by.edu.server.web;

import by.edu.server.beans.users.User;
import by.edu.server.properties.resources.WebConstants;
import by.edu.server.services.api.UserService;
import by.edu.server.utils.api.TokenManager;
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
    @Autowired
    private TokenManager tokenManager;
    @Value("${not-allowed}")
    private String notAllowed;
    @Value("${self-registration}")
    private String selfRegistration;



    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = WebConstants.APPLICATION)
    public DtoShell<User> logIn(@RequestBody DtoShell<UserSimpleDto> loginDto) {
        UserSimpleDto request = loginDto.getData();
        DtoShell<User> response = new DtoShell<>();
        User user = null;
        String token = null;
        try {
            request.setPassword(tokenManager.hash(request.getPassword()));
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

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = WebConstants.APPLICATION)
    public DtoShell<String> register(@RequestBody DtoShell<User> userDto) {
        DtoShell<String> response = new DtoShell<>();
        try {
            Boolean isSelfRegistrationAllowed = Boolean.parseBoolean(selfRegistration);
            if (!isSelfRegistrationAllowed) {
                response.setMessage(notAllowed);
                return response;
            }
            User user = userDto.getData();
            user.setPassword(tokenManager.hash(user.getPassword()));
            userService.addUser(user);
            response.setSuccessful(true);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET, produces = WebConstants.APPLICATION)
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

    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = WebConstants.APPLICATION)
    public DtoShell<String> logOut(@RequestHeader(value = WebConstants.AUTHORIZATION) String token, HttpServletRequest request) {
        DtoShell<String> response = new DtoShell<>();
        try {
            tokenManager.delete(token);
            response.setSuccessful(true);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
        return response;
    }
}

