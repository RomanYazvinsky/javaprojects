package com.senla.hotel.server;

import utilities.Input;
import utilities.Printer;

public class ServerControl extends  Thread {
    private  Server server;
    public ServerControl(Server server) {
        this.server = server;
    }
    @Override
    public void run(){
        Printer.print("\nEnter \"f\" to force stop, anything else to soft stop\n");
        if (Input.userInput().equals("f")) server.forceStop();
        else {
            server.stop();
        }
    }
}
