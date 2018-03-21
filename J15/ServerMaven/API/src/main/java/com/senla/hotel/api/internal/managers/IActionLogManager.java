package com.senla.hotel.api.internal.managers;

import com.senla.hotel.entities.ActionLog;
import com.senla.hotel.entities.User;
import com.senla.hotel.exceptions.DatabaseConnectException;
import com.senla.hotel.exceptions.QueryFailureException;

public interface IActionLogManager extends IGenericManager<ActionLog> {
    void log(User user, String message) throws QueryFailureException, DatabaseConnectException;
}
