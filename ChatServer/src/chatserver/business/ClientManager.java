/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver.business;

import chatserver.business.Message.Services;
import chatserver.utils.MsgUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class ClientManager implements Runnable {

    private static ArrayList<Client> clientList = new ArrayList<>();
    private static ArrayList<Message> msgList = new ArrayList<>();

    private static final int INT_SIZE = 4;

    /**
     * This class implements a Client Manager to store the list of connected clients to the server.
     */
    public ClientManager() {
    }

    /**
     * Gets the list of connected clients.
     * @return 
     */
    public static ArrayList<Client> getClientList() {
        return clientList;
    }

    /**
     * Adds a new client to the list.
     * @param c The <code>Client</code> to be added.
     */
    public static void addClient(Client c) {
        clientList.add(c);
    }

    /**
     * Removes a client from the list.
     * @param c The <code>Client</code> to be removed.
     */
    public static void removeClient(Client c) {
        clientList.remove(c);
    }

    /**
     * Adds a message to be broadcasted to the clients.
     * @param msg The <code>Message</code> to be broadcasted.
     */
    public static void addMessage(Message msg) {
        msgList.add(msg);
    }

    /**
     * Broadcasts the messages on the list.
     * @param msg 
     */
    private static void broadcastMessage(Message msg) {

        byte[] msgToBeSent = Message.getMsgAsByteVector(msg);

        for (Client c : clientList) {
            if (c.isConnected()) {
                c.sendMessage(msgToBeSent);
            }
        }

    }

    @Override
    public void run() {

        while (true) {

            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {

            }

            if (msgList.size() > 0) { // If theres at least one Message in the list, broadcast it.

                Message msg = msgList.remove(0);

                broadcastMessage(msg);

            }

        }

    }

}
