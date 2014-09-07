/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient.business;

import chatclient.business.Message.Services;
import chatclient.exceptions.InvalidNickName;
import chatclient.utils.MsgUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private static ArrayList<Message> msgList = new ArrayList<>();
    static ArrayList<String> stringList = new ArrayList<>();

    private static Client client;

    private static final int INT_SIZE = 4;

    public MessageHandler(Client c) {
        client = c;
    }

    private static void HandleMessage(Message msg) throws IOException {

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

    private static void Handle0x01(byte[] data) throws IOException {

        String newMessage;

        if (!client.isIDSetted()) {
            client.setClientID(MsgUtils.byteVectorToInteger(data));
            client.setNick();
            client.setClientIDSetted();
            newMessage = "[SERVER]: Welcome to the chat " + client.getNick() + "!\n";
        } else {
            newMessage = "[SERVER]: Client " + String.valueOf(MsgUtils.byteVectorToInteger(data)) + " joined the chat!\n";
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

        String newMessage = "[SERVER]: Client " + id + " changed the nickname to " + nick + ".\n";

        stringList.add(newMessage);

    }

    private static void Handle0x03(int size, byte[] data) {

        int iD;
        int numberOfIDs = size / 4;
        byte[] idByte = new byte[INT_SIZE];
        
        Client.onlineClientsList.clear();

        for (int i = 0; i < numberOfIDs; i++) {
            for (int j = 0; j < INT_SIZE; j++) {
                idByte[j] = data[j + i * INT_SIZE];
            }
            iD = MsgUtils.byteVectorToInteger(idByte);
            Client.onlineClientsList.add(new OnlineClient(iD));
        }

        synchronized (client.staff) {
            client.staff.notify();
        }

    }

    private static void Handle0x04(byte[] data) {

        client.lastOnlineNick = MsgUtils.byteVectorToString(data);

        synchronized (client.staff) {
            client.staff.notify();
        }

    }

    private static void Handle0x05(int size, byte[] data) {

        String msg = "";

        int msgSize = size - 2 * INT_SIZE;

        byte[] fromID = new byte[4];
        byte[] toID = new byte[4];
        byte[] msgArray = new byte[size - 2 * INT_SIZE];

        System.arraycopy(data, 0, fromID, 0, INT_SIZE);
        System.arraycopy(data, 4, toID, 0, INT_SIZE);
        System.arraycopy(data, 8, msgArray, 0, msgSize);

        int from = MsgUtils.byteVectorToInteger(fromID);
        int to = MsgUtils.byteVectorToInteger(toID);

        if (to != 0) {

            if (from != client.getClientID()) {
                
                String nick = "";

                for (OnlineClient oc : Client.onlineClientsList) {
                    if (from == oc.getClientID()) {
                        nick = oc.getNick();
                    }
                }

                msg += "[FROM " + nick + "]: ";
            } else {
                String nick = "";

                for (OnlineClient oc : Client.onlineClientsList) {
                    if (to == oc.getClientID()) {
                        nick = oc.getNick();
                    }
                }

                msg += "[TO " + nick + "]: ";
            }
            
        } else {

            String nick = "";

            for (OnlineClient oc : Client.onlineClientsList) {
                if (from == oc.getClientID()) {
                    nick = oc.getNick();
                }
            }

            msg += "[GLOBAL " + nick + "]: ";
        }

        msg += MsgUtils.byteVectorToString(msgArray);

        if (!msg.endsWith("\n")) {
            msg += "\n";
        }

        stringList.add(msg);

    }

    private static void Handle0x0A(byte[] data) {

        int receivedId = MsgUtils.byteVectorToInteger(data);

        if (receivedId == client.getClientID()) {
            System.exit(0);
        } else {
            stringList.add("[SERVER]: Client " + String.valueOf(receivedId) + " left the chat!\n");
        }

    }

    private static void Handle0x7F(byte[] data) {
        
        String serverMessage = "";

        switch (Nack.getNack(data[0])) {

            case InvalidChecksum:
                serverMessage = "[ERROR]: Failed to send message!\n";
                client.invalidChecksumStack();
                break;

            case NotGreetedClient:
                serverMessage = "[ERROR]: Not greeted client!\n";
                break;

            case BadFormedMessage:
                break;

            case UnidentifiedClient:
                serverMessage = "[ERROR]: Unidentified client!\n";
                break;

            case ExistentClient:
                serverMessage = "[ERROR]: \n";
                break;
                
            case NotAvailableNick:
                serverMessage = "[ERROR]: Someone is already using this nickname!\n";
                break;
                
            case InvalidNick:
                break;

        }
        
        MessageHandler.stringList.add(serverMessage);

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

                try {
                    MessageHandler.HandleMessage(msg);
                } catch (IOException ex) {
                    return;
                }
                synchronized (this) {
                    notify();
                }

            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }
        }
    }

}
