/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chat_server.client;

import chat_server.protocol.Message;
import chat_server.server.ClientManager;
import chat_server.utils.MessageUtils;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class Client implements Runnable {

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private int ClientID;
    private boolean isConnected = false;
    
    private static int lastID = 0;
    
    /**
     * Creates a Client object that represents the connection of a server's client.
     * @param sock Socket of the connection.
     * @throws IOException 
     */
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
    
    @Override
    public void run() {
        
        //System.out.println("WELCOME> Cliente ID " + this.ClientID + " entrou. (" + socket.getInetAddress().getHostAddress() + ")");
        
        while (true) {
            
            boolean isReady = false;
            byte service;
            byte[] size = new byte[4];
            byte[] data;
            byte[] checksum = new byte[4];
            
            do {
                
                try {
                    
                    service = this.input.readByte();
                    size[0] = this.input.readByte();
                    size[1] = this.input.readByte();
                    size[2] = this.input.readByte();
                    size[3] = this.input.readByte();
                    data = new byte[MessageUtils.byteVectorToInteger(size)];
                    
                    for ( int i=0;i<MessageUtils.byteVectorToInteger(size);i++ ) {
                        data[i] = this.input.readByte();
                    }
                    
                    checksum[0] = this.input.readByte();
                    checksum[1] = this.input.readByte();
                    checksum[2] = this.input.readByte();
                    checksum[3] = this.input.readByte();
                    
                } catch (IOException ex) {
                    System.out.println("CLOSE> Client ID " + this.ClientID + " left. ("   + socket.getInetAddress().getHostAddress() + ")");
                    return;
                }
                
                Message msg = new Message(service, size, data,checksum);
                msg.printMsg();
                
            } while(!isReady);
            
        }
        
    }
    
    @Override
    protected void finalize() {
        try {
            this.input.close();
        } catch (IOException e) { }
        try {
            this.output.close();
        } catch (IOException e) { }
        try {
            this.socket.close();
        } catch (IOException e) { }
    }
    
}
