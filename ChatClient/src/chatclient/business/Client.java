/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient.business;

import chatclient.business.Message.Services;
import chatclient.utils.MsgUtils;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author strudel
 */
public class Client implements Runnable {

    private enum UserOptions {

        HelpOption("/h"),
        ChangeNickOption("/c"),
        PrivateMessageOption("/w"),
        InvalidOption("");

        private String option;

        private UserOptions(String option) {
            this.option = option;
        }

        public static UserOptions getOption(String s) {

            UserOptions op = null;

            switch (s) {
                case "/h":
                    op = HelpOption;
                    break;

                case "/c":
                    op = ChangeNickOption;
                    break;

                case "/w":
                    op = PrivateMessageOption;
                    break;

            }

            return op;

        }

    };

    private static final String HELP_MESSAGE = "[/option] [parameters] [Message]\n"
            + "\nwhere options include:\n"
            + "\t/h\tShow this help message\n"
            + "\t/c\tSend requisition to change your nickname\n"
            + "\t/w\tSend private message to specified ID available in the client list\n";

    private static final String REQUEST_SENT = "REQUEST SENT TO SERVER!\n";

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    private int ClientID;
    private boolean isIDSetted = false;
    
    private static ArrayList<String> msgList = new ArrayList<>();
    
    private static ArrayList<Integer> idList = new ArrayList<>();
    private static ArrayList<String> nickList = new ArrayList<>();

    private static final int BYTES_PER_CHAR = 2;
    private static final int INT_SIZE = 4;
    private static final int FROM_TO_ID_SIZE = 8;
    private static final int CHECKSUM_FIELD_SIZE = 2;

    private static final int GLOBAL_ID = 0;
    private static final int USER_ID = 0;

    public Client(String ip, int port, String nick) throws IOException {

        this.socket = new Socket(ip, port);
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
        
        MessageHandler msgHandler = new MessageHandler(this);

        try {
            this.getConnected(nick);
        } catch (IOException e) {

        }

    }
    
    public int getClientID() {
        return this.ClientID;
    }

    public void setClientID(int id) {
        this.ClientID = id;
    }
    
    public boolean isIDSetted() {
        return this.isIDSetted;
    }
    
    public void setClientIDSetted() {
        this.isIDSetted = true;
    }

    private void sendMuchBytes(byte[] msg) throws IOException {

        for (byte b : msg) {
            this.output.writeByte(b);
        }

        Message.printMessage(msg);

    }

    private void getConnected(String nick) throws IOException {

        byte[] msg = Message.getMsgAsByteVector(new Message(Services.HelloService.getByte(), nick.length() * BYTES_PER_CHAR, MsgUtils.stringToByteVector(nick)));

        sendMuchBytes(msg);

    }

    private void changeNickName(String newNick) throws IOException {

        byte[] msg = Message.getMsgAsByteVector(new Message(Services.ChangeNickService.getByte(), newNick.length() * BYTES_PER_CHAR, MsgUtils.stringToByteVector(newNick)));

        sendMuchBytes(msg);

    }
    
    public void requestClientIDs() throws IOException {
        
        byte[] msg = Message.getMsgAsByteVector(new Message(Services.ConnectedClientsService.getByte(), 0, new byte[]{}));
        
        sendMuchBytes(msg);
        
    }

    private void sendMessage(String msg, int FROM, int TO) throws IOException {

        System.out.print("MESSAGE: " + msg);

        byte[] data = new byte[FROM_TO_ID_SIZE + msg.length() * BYTES_PER_CHAR];

        System.arraycopy(MsgUtils.integerToByteVector(FROM), 0, data, 0, 4);
        System.arraycopy(MsgUtils.integerToByteVector(TO), 0, data, 4, 4);
        System.arraycopy(MsgUtils.stringToByteVector(msg), 0, data, 8, msg.length() * BYTES_PER_CHAR);

        byte[] byteMsg = Message.getMsgAsByteVector(new Message(Services.SendMsgService.getByte(), data.length, data));

        sendMuchBytes(byteMsg);

    }
    
    public void sendGoodbye() throws IOException {
        
        System.out.println("Sending request to exit!");
        
        byte[] byteMsg = Message.getMsgAsByteVector(new Message(Services.ByeService.getByte(),0,new byte[]{}));
        
        sendMuchBytes(byteMsg);
        
    }

    private Message receiveMessage() throws IOException {

        byte service;
        int size;
        byte[] data;
        int checksum;

        service = this.input.readByte();
        size = this.input.readShort();

        data = new byte[size];

        for (int i = 0; i < size; i++) {
            data[i] = this.input.readByte();
        }

        return new Message(service, size, data);

    }
    
    public String DecodeUserMessage(String msg) throws IOException {

        String feedBack = "";

        if (msg.startsWith("/")) {

            String[] splittedMsg = msg.split(" ");

            switch (UserOptions.getOption(splittedMsg[0])) {

                case HelpOption:
                    feedBack = HELP_MESSAGE;
                    break;

                case ChangeNickOption:
                    this.changeNickName(splittedMsg[1]);
                    break;

                case PrivateMessageOption:
                    this.sendMessage(splittedMsg[2], USER_ID, Integer.parseInt(splittedMsg[1]));
                    break;

                case InvalidOption:
                    feedBack = HELP_MESSAGE;
                    break;

            }

        } else {
            this.sendMessage(msg, USER_ID, GLOBAL_ID);
        }

        return feedBack;

    }

    @Override
    public void run() {

        while (true) {

            try {

                Message msg = this.receiveMessage();
                int checksum = this.input.readShort();
                if (Message.getCheckSum(msg) == checksum) {
                    System.out.print("FROM " + this.ClientID + ": ");
                    Message.printMessage(Message.getMsgAsByteVector(msg));
                    MessageHandler.addMessage(msg);
                }

            } catch (IOException e) {

            }

        }

    }

}
