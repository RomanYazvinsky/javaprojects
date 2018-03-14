package com.senla.hotel.ui;

import com.senla.hotel.api.ui.IBuilder;
import com.senla.hotel.api.ui.IMenuController;
import com.senla.hotel.api.ui.INavigator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import utilities.injection.DependencyInjector;
import utilities.ui.Input;
import utilities.ui.Printer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MenuController implements IMenuController {
    private static org.apache.logging.log4j.Logger logger = LogManager.getLogger(MenuController.class);
    private String hello = "EXAMPLE OF SETTING PARAMS: 12, Roma, 17 12 2017, 19 12 2017";
    private IBuilder builder;
    private INavigator navigator;


    public MenuController() {
        builder = (IBuilder) DependencyInjector.newInstance(IBuilder.class);
        builder.buildMenu();
        navigator = (INavigator) DependencyInjector.newInstance(INavigator.class);
        navigator.setCurrentMenu(builder.getRootMenu());
    }

    public void setIO(ObjectOutputStream writer, ObjectInputStream reader) {
        navigator.setIO(writer, reader);
    }

    @Override
    public void run() {
        Printer.println(hello);
        int index = 0;
        do {
            navigator.printMenu();
            try {
                index = Integer.parseInt(Input.userInput()) - 1;
                navigator.navigate(index);
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                logger.log(Level.DEBUG, e.getMessage());
                continue;
            }

        } while (navigator.isNotEnd());
        Input.input.close();
    }
}
