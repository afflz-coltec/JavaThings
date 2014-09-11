/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver.business;

import chatserver.business.Message.Services;
import chatserver.business.MessageHandler.Nack;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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
    private String nickName = "";

    private int wrongChecksumStack = 0;

    private static int lastID = 0;

    /**
     * This class implements a client that connected to this server.
     *
     * @param sock
     * @throws IOException
     */
    public Client(Socket sock) throws IOException {
        this.socket = sock;
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
        this.ClientID = ++lastID;

        ClientManager.addClient(this); // Add the client to the client list.
    }

    /**
     * Gets the client's socket.
     *
     * @return The client's <code>Socket</code>.
     */
    public Socket getSocket() {
        return this.socket;
    }

    /**
     * Gets the client's identification number.
     *
     * @return A <code>int</code> identification number.
     */
    public int getClientID() {
        return this.ClientID;
    }

    /**
     * Gets the client's nickname.
     *
     * @return A <code>String</code> with the nickname.
     */
    public String getNickName() {
        return this.nickName;
    }

    /**
     * Gets the connection state of the client.
     *
     * @return <code>true</code> if the client is connected.
     */
    public boolean isConnected() {
        return this.isConnected;
    }

    /**
     * Sets the client's nickname;
     *
     * @param nickName A <code>String</code> containing the nickname.
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * Set the connection state of the client.
     *
     * @param isConnected <code>true</code> for connected or <code>false</code>
     * for disconnected.
     */
    public void setConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    /**
     * Sends a message to the client connected to the server.
     *
     * @param msg A <code>byte[]</code> containing the message to be sent.
     * @throws IOException
     */
    public void sendMessage(byte[] msg) {

        try {
            System.out.print("TO " + this.ClientID + ": ");

            for (byte b : msg) {
                this.output.writeByte(b);
            }

            Message.printMessage(msg);
        } catch (IOException e) {
            ClientManager.removeClient(this);
        }

    }

    /**
     * Method to get messages from the client.
     *
     * @return an object <code>Message</code>.
     * @throws IOException
     */
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
                    synchronized (Server.msgHandler) {
                        Server.msgHandler.notify();
                    }
                } else {
                    this.wrongChecksumStack++;
                    Message wrongCheckSum = new Message(Services.DeniedService.getByte(),1,new byte[]{Nack.InvalidChecksum.getByte()});
                    byte[] wrongCheckSumAnswer = Message.getMsgAsByteVector(wrongCheckSum);
                    this.sendMessage(wrongCheckSumAnswer);
                }

                if (this.wrongChecksumStack == 3) {
                    ClientManager.removeClient(this);
                    return;
                }

            } catch (IOException ex) {
                ClientManager.removeClient(this);
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
