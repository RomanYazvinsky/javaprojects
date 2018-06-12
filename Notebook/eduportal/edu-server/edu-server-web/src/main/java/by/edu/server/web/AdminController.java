package by.edu.server.web;

import by.edu.server.beans.enumerations.Role;
import by.edu.server.beans.users.User;
import by.edu.server.properties.resources.WebConstants;
import by.edu.server.services.api.UserService;
import by.edu.server.utils.api.TokenManager;
import by.edu.server.web.dto.DtoShell;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {
    private static Logger logger = LogManager.getLogger(AdminController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private ModelMapper modelMapper;
    @Value("${validation-fail}")
    private String validationFailed;


    private User defineUser(String token) {
        User user = tokenManager.get(token);
        return user.getRole().equals(Role.ADMIN) ? user : null;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST, produces = WebConstants.APPLICATION)
    public DtoShell<Boolean> editUser(@RequestHeader(value = WebConstants.AUTHORIZATION) String token, @RequestBody DtoShell<User> userDto) {
        DtoShell<Boolean> response = new DtoShell<>();
        if (defineUser(token) == null) {
            response.setMessage(validationFailed);
            return response;
        }
        try {
            User user = userDto.getData();
            String hashedPassword = tokenManager.hash(user.getPassword());
            userService.updateUser(userDto.getData(), hashedPassword);
            response.setSuccessful(true);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = WebConstants.APPLICATION)
    public DtoShell<User> getUser(@RequestHeader(value = WebConstants.AUTHORIZATION) String token,
                                  @PathVariable int id) {
        DtoShell<User> response = new DtoShell<>();
        if (defineUser(token) == null) {
            response.setMessage(validationFailed);
            return response;
        }
        try {
            response.setData(userService.getUser(id, true));
            response.setSuccessful(true);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = WebConstants.APPLICATION)
    public DtoShell<String> register(@RequestHeader(value = WebConstants.AUTHORIZATION) String token, @RequestBody DtoShell<User> userDto) {
        DtoShell<String> response = new DtoShell<>();
        try {
            if (defineUser(token) == null) {
                response.setMessage(validationFailed);
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
}
