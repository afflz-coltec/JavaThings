/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chat_server.server;

import chat_server.client.Client;
import chat_server.protocol.Message;
import java.util.ArrayList;

/**
 *
 * @author pedro
 */
public class ClientManager implements Runnable {

    private static ArrayList<Client> clientList = new ArrayList<>();
    private static ArrayList<byte[]> msgList = new ArrayList<>();
    private static ArrayList<String> nickList = new ArrayList<>();
    
    public ClientManager() { }
    
    public static void addClient(Client c) {
        clientList.add(c);
    }
    
    public void MessageHandler(Message msg, Client c) {
        
        switch(msg.getService()) {
            
            case HelloService:
                if ( (msg.getSize() == msg.getData().length()) && !c.isConnected() ) {
                    c.setNickName(msg.getData());
                    c.setConnected();
                }
                break;
                
            case ChangeNickService:
                break;
                
            case ConnectedClientsService:
                break;
                
            case GetNickService:
                break;
                
            case SendMsgService:
                break;
                
            case ByeService:
                break;
            
        }
        
    }
    
    @Override
    public void run() {
       
        
    }
    
}
