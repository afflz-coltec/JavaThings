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
import chatserver.utils.MsgUtils;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author pedro
 */
public class MessageHandler implements Runnable {

    /**
     * Enumeration of the Nacks.
     */
    public enum Nack {

        InvalidChecksum     ((byte) 0xFF),
        NotGreetedClient    ((byte) 0xEE),
        BadFormedMessage    ((byte) 0xDD),
        UnidentifiedClient  ((byte) 0xCC),
        RegisteredClient    ((byte) 0xBB);

        private final byte nackByte;

        private Nack(byte nackByte) {
            this.nackByte = nackByte;
        }

        /**
         * Gets the byte of a nack.
         * @return Nack's <code>byte</code>.
         */
        public byte getByte() {
            return this.nackByte;
        }

        /**
         * Gets a Nack by byte.
         * @param b The nack <code>byte</code>.
         * @return A <code>Nack</code> element.
         */
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
                    n = RegisteredClient;
                    break;

            }

            return n;

        }

    };

    private static ArrayList<Request> requestList = new ArrayList<>();

    private static final int BYTES_PER_CHAR = 2;
    private static final int INT_SIZE = 4;

    public MessageHandler() {
        
    }

    /**
     * Adds a new request to be handled.
     * @param r an object <code>Request</code>.
     */
    public static void addNewRequest(Request r) {
        requestList.add(r);
    }

    /**
     * Generic method to define which handler is going to be used.
     * @param client The client who sent the message.
     * @param msg A <code>Message</code> to be handled.
     * @throws IOException 
     */
    private static void HandleMessage(Client client, Message msg) {

        if (msg.getSize() < 0) {

            Message badformedmsg = new Message(Services.DeniedService.getByte(), 1, new byte[]{Nack.BadFormedMessage.getByte()});
            byte[] badformed = Message.getMsgAsByteVector(badformedmsg);
            client.sendMessage(badformed);

        } else {

            switch (Services.getService(msg.getService())) {

                case HelloService:
                    Handle0x01(client, msg.getData());
                    break;

                case ChangeNickService:
                    Handle0x02(client, msg.getData());
                    break;

                case ConnectedClientsService:
                    Handle0x03(client, msg.getData());
                    break;

                case RequestNickService:
                    Handle0x04(client, msg.getData());
                    break;

                case SendMsgService:
                    Handle0x05(client, msg.getData());
                    break;

                case ByeService:
                    Handle0x0A(client);
                    break;

                default:
                    Message badformed = new Message(Services.DeniedService.getByte(), 1, new byte[]{Nack.BadFormedMessage.getByte()});
                    byte[] badformedmsg = Message.getMsgAsByteVector(new Message(Nack.BadFormedMessage.getByte(), 0, new byte[]{}));
                    break;

            }
        }
    }

    /**
     * A Handler to the service <code>HelloService</code>.
     * @param client The client who sent the message.
     * @param data The data of the <code>Message</code>.
     * @throws IOException 
     */
    private static void Handle0x01(Client client, byte[] data) {

        String nick = MsgUtils.byteVectorToString(data);
        boolean isAvailable = true;
        byte[] answer;

        if ( !client.isConnected() ) {
            
            ArrayList<Client> clientList = ClientManager.getClientList();
            
            if (clientList.size() > 0) {
                for (Client c : clientList) {
                    if (c.getNickName().equals(nick) && c.isConnected()) {
                        isAvailable = false;
                    }
                }
            }

            if (!isAvailable) {
                answer = Message.getMsgAsByteVector(new Message(Services.DeniedService.getByte(), 1, new byte[]{Services.ByeService.getByte()}));
                client.sendMessage(answer);
            } else {
                client.setNickName(nick);
                client.setConnected(true);
                ClientManager.addMessage(new Message(Services.HelloService.getByte(), INT_SIZE, MsgUtils.integerToByteVector(client.getClientID())));
                synchronized (Server.clientManager) {
                    Server.clientManager.notify();
                }
            }
            
        }
        else {
            Message registered = new Message(Services.DeniedService.getByte(),1,new byte[]{Nack.RegisteredClient.getByte()});
            byte[] registeredAnswer = Message.getMsgAsByteVector(registered);
            client.sendMessage(registeredAnswer);
        }

    }

    /**
     * A Handler to the service <code>ChangeNickService</code>.
     * @param client The client who sent the message.
     * @param data The data of the <code>Message</code>.
     */
    private static void Handle0x02(Client client, byte[] data) {

        if ( client.isConnected() ) {
            
            String nick = MsgUtils.byteVectorToString(data);
            boolean isAvailable = true;
            byte[] dataToBeSent;
            byte[] answerToClient = null;

            ArrayList<Client> clientList = ClientManager.getClientList();

            if (clientList.size() > 0) {
                for (Client c : clientList) {
                    if (c.getNickName().equals(nick)) {
                        isAvailable = false;
                        break;
                    }
                }
            }

            if (!isAvailable) {
                answerToClient = Message.getMsgAsByteVector(new Message(Services.DeniedService.getByte(),
                        1,
                        new byte[]{Services.ChangeNickService.getByte()})
                );
                client.sendMessage(answerToClient);
            } else {
                client.setNickName(nick);
                int dataSize = INT_SIZE + nick.length() * BYTES_PER_CHAR;
                dataToBeSent = new byte[dataSize];

                System.arraycopy(MsgUtils.integerToByteVector(client.getClientID()), 0, dataToBeSent, 0, INT_SIZE);
                System.arraycopy(MsgUtils.stringToByteVector(nick), 0, dataToBeSent, 4, nick.length() * BYTES_PER_CHAR);

                ClientManager.addMessage(new Message(Services.ChangeNickService.getByte(),
                        dataSize,
                        dataToBeSent));

                synchronized (Server.clientManager) {
                    Server.clientManager.notify();
                }

            }
            
        }
        else {
            Message notgreeted = new Message(Services.DeniedService.getByte(), 1, new byte[]{Nack.NotGreetedClient.getByte()});
            byte[] notGreetedAnswer = Message.getMsgAsByteVector(notgreeted);
            client.sendMessage(notGreetedAnswer);
        }

    }

    /**
     * A Handler to the service <code>ConnectedClientsService</code>.
     * @param client The client who sent the message.
     * @param data The data of the <code>Message</code>.
     */
    private static void Handle0x03(Client client, byte[] data) {

        if ( client.isConnected() ) {
            
            ArrayList<Client> clientList = ClientManager.getClientList();
            ArrayList<byte[]> idList = new ArrayList<>();

            for (Client c : clientList) {
                if (c.isConnected()) {
                    idList.add(MsgUtils.integerToByteVector(c.getClientID()));
                }
            }

            byte[] clientIDs = new byte[idList.size() * INT_SIZE];

            for (int i = 0; i < idList.size(); i++) {
                System.arraycopy(idList.get(i), 0, clientIDs, i * INT_SIZE, INT_SIZE);
            }

            byte[] answer = Message.getMsgAsByteVector(new Message(Services.ConnectedClientsService.getByte(), clientIDs.length, clientIDs));

            client.sendMessage(answer);
            
        }
        else {
            Message notgreeted = new Message(Services.DeniedService.getByte(), 1, new byte[]{Nack.NotGreetedClient.getByte()});
            byte[] notGreetedAnswer = Message.getMsgAsByteVector(notgreeted);
            client.sendMessage(notGreetedAnswer);
        }

    }

    /**
     * A Handler to the service <code>RequestNickService</code>.
     * @param client The client who sent the message.
     * @param data The data of the <code>Message</code>. 
     */
    private static void Handle0x04(Client client, byte[] data) {

        if (client.isConnected()) {
            int clientID = MsgUtils.byteVectorToInteger(data);
            String nick = "";
            byte[] answer;

            for (Client c : ClientManager.getClientList()) {
                if (c.isConnected() && c.getClientID() == clientID) {
                    nick = c.getNickName();
                }
            }

            if (nick.equals("")) {
                answer = Message.getMsgAsByteVector(new Message(Services.DeniedService.getByte(), 1, new byte[]{Nack.UnidentifiedClient.getByte()}));
            } else {
                answer = Message.getMsgAsByteVector(new Message(Services.RequestNickService.getByte(),
                        nick.length() * BYTES_PER_CHAR,
                        MsgUtils.stringToByteVector(nick)));
            }
            client.sendMessage(answer);
        }
        else {
            Message notgreeted = new Message(Services.DeniedService.getByte(), 1, new byte[]{Nack.NotGreetedClient.getByte()});
            byte[] notGreetedAnswer = Message.getMsgAsByteVector(notgreeted);
            client.sendMessage(notGreetedAnswer);
        }

    }

    /**
     * A Handler to the service <code>SendMsgService</code>.
     * @param client The client who sent the message.
     * @param data The data of the <code>Message</code>. 
     */
    private static void Handle0x05(Client client, byte[] data) {

        if (client.isConnected()) {
            
            int msgLength = data.length - 2 * INT_SIZE;

            byte[] answer;
            byte[] toID = new byte[4];
            byte[] dataToBeSent = new byte[data.length];
            byte[] fromID = MsgUtils.integerToByteVector(client.getClientID());

            System.arraycopy(data, 4, toID, 0, INT_SIZE);

            int toClient = MsgUtils.byteVectorToInteger(toID);

            System.arraycopy(fromID, 0, dataToBeSent, 0, INT_SIZE);
            System.arraycopy(toID, 0, dataToBeSent, 4, INT_SIZE);
            System.arraycopy(data, 8, dataToBeSent, 8, msgLength);

            Message msgToBeSent = new Message(Services.SendMsgService.getByte(), dataToBeSent.length, dataToBeSent);

            answer = Message.getMsgAsByteVector(msgToBeSent);

            if (toClient == 0) {
                ClientManager.addMessage(msgToBeSent);
                synchronized (Server.clientManager) {
                    Server.clientManager.notify();
                }
            } else {
                Client tmp = null;
                for (Client c : ClientManager.getClientList()) {
                    if (toClient == c.getClientID()) {
                        tmp = c;
                    }
                }
                
                if ( tmp != null ) {
                    tmp.sendMessage(answer);
                    client.sendMessage(answer);
                }
                else {
                    Message notIdentified = new Message(Services.DeniedService.getByte(),1,new byte[]{Nack.UnidentifiedClient.getByte()});
                    byte[] notIdentifiedAnswer = Message.getMsgAsByteVector(notIdentified);
                    client.sendMessage(notIdentifiedAnswer);
                }
            }
            
        }
        else {
            Message notgreeted = new Message(Services.DeniedService.getByte(), 1, new byte[]{Nack.NotGreetedClient.getByte()});
            byte[] notGreetedAnswer = Message.getMsgAsByteVector(notgreeted);
            client.sendMessage(notGreetedAnswer);
        }

    }

    /**
     * A Handler to the service <code>ByeService</code>.
     * @param client The client who sent bye.
     */
    private static void Handle0x0A(Client client) {

        if (client.isConnected()) {
            byte[] byteID = MsgUtils.integerToByteVector(client.getClientID());
            Message msg = new Message(Services.ByeService.getByte(), INT_SIZE, byteID);

            client.sendMessage(Message.getMsgAsByteVector(msg));

            ClientManager.addMessage(msg);

            ClientManager.removeClient(client);

            synchronized (Server.clientManager) {
                Server.clientManager.notify();
            }
        }
        else {
            Message notgreeted = new Message(Services.DeniedService.getByte(), 1, new byte[]{Nack.NotGreetedClient.getByte()});
            byte[] notGreetedAnswer = Message.getMsgAsByteVector(notgreeted);
            client.sendMessage(notGreetedAnswer);
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

            if (requestList.size() > 0) {

                Request request = requestList.remove(0);

                HandleMessage(request.getClient(), request.getMsg());

            }

        }

    }

}
