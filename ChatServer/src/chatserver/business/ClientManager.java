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

/**
 *
 * @author pedro
 */
public class ClientManager implements Runnable {
    
    private static ArrayList<Client> clientList = new ArrayList<>();
    
    private static final int INT_SIZE = 4;
    
    public ClientManager() { }
    
    public static void addClient(Client c) {
        clientList.add(c);
    }
    
    private void broadcastMessage(Message msg) throws IOException {
        
        for ( Client c : clientList )
            c.sendMessage(msg);
        
    }
    
    private void privateMessage(Message msg, int iD) {
        
    }
    
    public static void HandleMessage(Message msg, int checksum, Client c) throws IOException {
        
        switch(Services.getService(msg.getService())) {
            
            case HelloService:
                
                if ( !c.isConnected() ) {
                    c.setNickName(MsgUtils.byteVectorToString(msg.getData()));
                    c.setConnected(true);
                    c.sendMessage(new Message(Services.HelloService.getByte(), INT_SIZE, MsgUtils.integerToByteVector(c.getClientID())));
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
