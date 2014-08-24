/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chat_server.server;

import chat_server.client.Client;
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
    private static ArrayList<byte[]> msgList = new ArrayList<>();
    
    public ClientManager() { }
    
    public static void addClient(Client c) {
        clientList.add(c);
    }
    
    @Override
    public void run() {
       
        
    }
    
}
