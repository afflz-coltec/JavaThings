/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chatserver.business;

import chatserver.business.Message.Services;
import chatserver.utils.MsgUtils;
import java.util.ArrayList;

/**
 *
 * @author pedro
 */
public class ClientManager implements Runnable {
    
    public enum Nack {
        
        InvalidChecksum     ((byte)0xFF),
        NotGreetedClient    ((byte)0xEE),
        BadFormedMessage    ((byte)0xDD),
        UnidentifiedClient  ((byte)0xCC),
        ExistentClient      ((byte)0xBB);
        
        private final byte nackByte;
        
        private Nack(byte nackByte) {
            this.nackByte = nackByte;
        }
        
        public byte getByte() {
            return this.nackByte;
        }
        
        public static Nack getNack(byte b) {
            
            Nack n = null;
            
            switch(b) {
                
                case 0x01:
                    n = InvalidChecksum;
                    break;
                    
                case 0x02:
                    n = NotGreetedClient;
                    break;
                    
                case 0x03:
                    n = BadFormedMessage;
                    break;
                    
                case 0x04:
                    n = UnidentifiedClient;
                    break;
                    
                case 0x05:
                    n = ExistentClient;
                    break;
                
            }
            
            return n;
            
        }
        
    }
    
    private static ArrayList<Client> clientList = new ArrayList<>();
    
    private static final int INT_SIZE = 4;
    
    public ClientManager() { }
    
    public static void addClient(Client c) {
        clientList.add(c);
    }
    
    public static void MessageHandler(Message msg, int checksum, Client c) {
        
        switch(Services.getService(msg.getService())) {
            
            case HelloService:
                
                if ( !c.isConnected() ) {
                    c.setNickName(MsgUtils.byteVectorToString(msg.getData()));
                    c.setConnected(true);
//                    c.sendMessage(new Message(Services.HelloService.getByte(), INT_SIZE, ));
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
