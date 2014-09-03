/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient.business;

import chatclient.business.Message.Services;
import chatclient.utils.MsgUtils;
import java.util.ArrayList;

/**
 *
 * @author pedro
 */
public class MessageHandler implements Runnable {

    public enum Nack {

        InvalidChecksum((byte) 0xFF),
        NotGreetedClient((byte) 0xEE),
        BadFormedMessage((byte) 0xDD),
        UnidentifiedClient((byte) 0xCC),
        ExistentClient((byte) 0xBB);

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

    };

    private static ArrayList<Message> msgList = new ArrayList<>();
    private static ArrayList<String> stringList = new ArrayList<>();

    private static Client client;

    private static final int INT_SIZE = 4;

    public MessageHandler(Client c) {
        client = c;
    }

    private static void HandleMessage(Message msg) {

        switch (Services.getService(msg.getService())) {

            case HelloService:
                Handle0x01(msg.getData());
                break;

            case ChangeNickService:
                Handle0x02(msg.getSize(), msg.getData());
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
        
        String newMessage;
        
        if ( !client.isIDSetted() ) {
            client.setClientID(MsgUtils.byteVectorToInteger(data));
            client.setClientIDSetted();
            newMessage = "Welcome to the chat " + String.valueOf(MsgUtils.byteVectorToInteger(data)) + "!\n";
        }
        else {
            newMessage = "Client " + String.valueOf(MsgUtils.byteVectorToInteger(data)) + " joined the chat!\n";
        }

        stringList.add(newMessage);

    }

    private static void Handle0x02(int size, byte[] data) {

        int nickSize = size - INT_SIZE;
        byte[] idArray = new byte[INT_SIZE];
        byte[] nickArray = new byte[nickSize];

        System.arraycopy(data, 0, idArray, 0, 4);
        System.arraycopy(data, 4, nickArray, 0, nickSize);

        String id = String.valueOf(MsgUtils.byteVectorToInteger(idArray));
        String nick = MsgUtils.byteVectorToString(nickArray);

        String newMessage = "Client " + id + " changed the nickname to " + nick + ".\n";

        stringList.add(newMessage);

    }

    private static void Handle0x05(int size, byte[] data) {

        int msgSize = size - 2 * INT_SIZE;

        byte[] fromID = new byte[4];
        byte[] toID = new byte[4];
        byte[] msgArray = new byte[size - 2 * INT_SIZE];

        System.arraycopy(data, 0, fromID, 0, INT_SIZE);
        System.arraycopy(data, 4, toID, 0, INT_SIZE);
        System.arraycopy(data, 8, msgArray, 0, msgSize);

        int from = MsgUtils.byteVectorToInteger(fromID);
        int to = MsgUtils.byteVectorToInteger(toID);
        String msg = MsgUtils.byteVectorToString(msgArray);
        
        if ( !msg.endsWith("\n") ) {
            msg += "\n";
        }

        stringList.add(msg);

    }

    private static void Handle0x0A(byte[] data) {
        
        int receivedId = MsgUtils.byteVectorToInteger(data);
        
        if ( receivedId == client.getClientID() )
            System.exit(0);
        else
            stringList.add("Client " + String.valueOf(receivedId) + " left the chat!\n");
        
    }
    
    private static void Handle0x7F(byte[] data) {
        
        switch(Nack.getNack(data[0])) {
            
            case InvalidChecksum:
                break;
                
            case NotGreetedClient:
                break;
                
            case BadFormedMessage:
                break;
                
            case UnidentifiedClient:
                break;
                
            case ExistentClient:
                break;
            
        }
        
    }
    
    public static final void addString(String s) {
        MessageHandler.stringList.add(s);
    }

    public static final void addMessage(Message msg) {
        MessageHandler.msgList.add(msg);
    }

    public static String getMessage() {
        return stringList.size() > 0 ? stringList.remove(0) : "";
    }

    @Override
    public void run() {

        while (true) {

            if (MessageHandler.msgList.size() > 0) {

                Message msg = msgList.remove(0);

                MessageHandler.HandleMessage(msg);

            }

            synchronized (this) {
                notify();
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }
        }
    }

}
