/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chatserver.business;

import chatserver.business.Message.Services;
import static chatserver.business.Message.Services.ByeService;
import static chatserver.business.Message.Services.ChangeNickService;
import static chatserver.business.Message.Services.ConnectedClientsService;
import static chatserver.business.Message.Services.DeniedService;
import static chatserver.business.Message.Services.HelloService;
import static chatserver.business.Message.Services.SendMsgService;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author pedro
 */
public class MessageHandler implements Runnable {

    public enum Nack {

        InvalidChecksum     ((byte) 0xFF),
        NotGreetedClient    ((byte) 0xEE),
        BadFormedMessage    ((byte) 0xDD),
        UnidentifiedClient  ((byte) 0xCC),
        ExistentClient      ((byte) 0xBB),
        InvalidNick         ((byte) 0x01),
        NotAvailableNick    ((byte) 0x02);

        private final byte nackByte;

        private Nack(byte nackByte) {
            this.nackByte = nackByte;
        }

        public byte getByte() {
            return this.nackByte;
        }

        public static Nack getNack(byte b) {

            Nack n = null;

            switch (b) {

                case (byte) 0xFF:
                    n = InvalidChecksum;
                    break;

                case (byte) 0xEE:
                    n = NotGreetedClient;
                    break;

                case (byte) 0xDD:
                    n = BadFormedMessage;
                    break;

                case (byte) 0xCC:
                    n = UnidentifiedClient;
                    break;

                case (byte) 0xBB:
                    n = ExistentClient;
                    break;
                    
                case (byte) 0x01:
                    n = InvalidNick;
                    break;
                    
                case (byte) 0x02:
                    n = NotAvailableNick;

            }

            return n;

        }
        
    };
    
    private static ArrayList<Request> requestList = new ArrayList<>();
    
    public MessageHandler() { }
    
    public static void addNewRequest(Request r) {
        requestList.add(r);
    }
    
    private static void HandleMessage(Client client,Message msg) throws IOException {

        switch (Services.getService(msg.getService())) {

            case HelloService:
                Handle0x01(msg.getData());
                break;

            case ChangeNickService:
                Handle0x02(msg.getSize(), msg.getData());
                break;

            case ConnectedClientsService:
                Handle0x03(msg.getSize(), msg.getData());
                break;

            case RequestNickService:
                Handle0x04(msg.getData());
                break;

            case SendMsgService:
                Handle0x05(msg.getSize(), msg.getData());
                break;

            case ByeService:
                Handle0x0A(msg.getData());
                break;

            case DeniedService:
                Handle0x7F(msg.getData());
                break;

        }
    }
    
    private static void Handle0x01(byte[] data) {
        
    }
    
    private static void Handle0x02(int size, byte[] data) {
        
    }
    
    private static void Handle0x03(int size, byte[] data) {
        
    }
    
    private static void Handle0x04(byte[] data) {
        
    }
    
    private static void Handle0x05(int size, byte[] data) {
        
    }
    
    private static void Handle0x0A(byte[] data) {
        
    }
    
    private static void Handle0x7F(byte[] data) {
        
    }
    
    @Override
    public void run() {
        
        while(true) {
            
            if ( requestList.size() > 0 ) {
                
                
                
            }
            
        }
        
    }
    
}
