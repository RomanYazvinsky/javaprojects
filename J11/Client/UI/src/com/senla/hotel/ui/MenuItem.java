package com.senla.hotel.ui;

import com.senla.hotel.api.internal.IAction;
import com.senla.hotel.api.internal.IMenu;
import com.senla.hotel.api.internal.IMenuItem;
import com.senla.hotel.exceptions.ActionForceStopException;
import com.senla.hotel.ui.actions.NoAction;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MenuItem implements IMenuItem {
    private static org.apache.logging.log4j.Logger logger = LogManager.getLogger(MenuItem.class);
    private String title;
    private IAction action;
    private IMenu nextMenu;


    public MenuItem(String title) {
        super();
        this.title = title;
        this.action = new NoAction();
        nextMenu = Builder.getInstance().getRootMenu();
    }

    public MenuItem(String title, IAction action) {
        super();
        this.title = title;
        this.action = action;
        nextMenu = Builder.getInstance().getRootMenu();
    }

    /* (non-Javadoc)
     * @see com.senla.hotel.ui.IMenuItem#setNextMenu(com.senla.hotel.api.internal.IMenu)
     */
    @Override
    public void setNextMenu(IMenu menu) {
        nextMenu = menu;
    }

    /* (non-Javadoc)
     * @see com.senla.hotel.ui.IMenuItem#doAction()
     */
    @Override
    public void doAction(ObjectOutputStream writer, ObjectInputStream reader) throws ActionForceStopException {
        try {
            action.execute(writer, reader);
        } catch (ActionForceStopException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    /* (non-Javadoc)
     * @see com.senla.hotel.ui.IMenuItem#getTitle()
     */
    @Override
    public String getTitle() {
        return title;
    }

    /* (non-Javadoc)
     * @see com.senla.hotel.ui.IMenuItem#getAction()
     */
    @Override
    public IAction getAction() {
        return action;
    }

    /* (non-Javadoc)
     * @see com.senla.hotel.ui.IMenuItem#getNextMenu()
     */
    @Override
    public IMenu getNextMenu() {
        return nextMenu;
    }

}
