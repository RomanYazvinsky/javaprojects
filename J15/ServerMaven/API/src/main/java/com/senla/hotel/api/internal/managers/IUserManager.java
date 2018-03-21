package com.senla.hotel.api.internal.managers;

import com.senla.hotel.constants.SortType;
import com.senla.hotel.entities.User;
import com.senla.hotel.exceptions.DatabaseConnectException;
import com.senla.hotel.exceptions.QueryFailureException;

import java.util.List;

public interface IUserManager {
    Boolean add(User user, boolean addId) throws QueryFailureException, DatabaseConnectException;

    User getUserById(Integer id) throws QueryFailureException, DatabaseConnectException;

    List<User> sort(SortType sortType) throws QueryFailureException, DatabaseConnectException;

    List<User> getUsers() throws QueryFailureException, DatabaseConnectException;

    User get(String username, String password) throws QueryFailureException, DatabaseConnectException;

    Boolean delete(User user) throws QueryFailureException, DatabaseConnectException;
}
