/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver.business;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author strudel
 */
public class Client implements Runnable {

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    private int ClientID;
    private boolean isConnected = false;
    private String nickName;

    private int wrongChecksumStack = 0;

    private static int lastID = 0;

    public Client(Socket sock) throws IOException {
        this.socket = sock;
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
        this.ClientID = ++lastID;

        ClientManager.addClient(this);
    }

    public Socket getSocket() {
        return this.socket;
    }

    public int getClientID() {
        return this.ClientID;
    }

    public String getNickName() {
        return this.nickName;
    }

    public boolean isConnected() {
        return this.isConnected;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public void sendMessage(Message msg) throws IOException {

        System.out.print("TO " + this.ClientID + ": ");

        for (byte b : Message.getMsgAsByteVector(msg)) {
            this.output.writeByte(b);
            System.out.print(String.format("%02X ", b));
        }

        System.out.println();

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

    @Override
    public void run() {

        while (true) {

            try {

                Message msg = this.receiveMessage();

                int checksum = this.input.readShort();

                if (Message.getCheckSum(msg) == checksum) {
                    System.out.print("FROM " + this.ClientID + ": ");
                    Message.printMessage(Message.getMsgAsByteVector(msg));
                    MessageHandler.addNewRequest(new Request(this, msg));
                } else {
                    this.wrongChecksumStack++;
                }
                
                if ( this.wrongChecksumStack == 3 ) {
                    setConnected(false);
                    return;
                }

            } catch (IOException ex) {
                this.setConnected(false);
                return;
            }

        }

    }

    @Override
    protected void finalize() {

        try {
            this.input.close();
            this.output.close();
            this.socket.close();
        } catch (IOException e) {
        }
    }

}
