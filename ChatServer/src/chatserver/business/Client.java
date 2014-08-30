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
        
        for ( byte b : Message.getMsgAsByteVector(msg) )
            this.output.writeByte(b);
        
    }

    @Override
    public void run() {
        
        while (true) {
            
            byte service = 0;
            int size = 0;
            byte[] data = null;
            int checksum = 0;
            
            do {
                
                try {
                    
                    service = this.input.readByte();
                    size =    this.input.readShort();
                    
                    data =    new byte[size];
                    
                    for ( int i=0; i<size; i++ )
                        data[i] = this.input.readByte();
                    
                    checksum = this.input.readShort();
                    
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                Message msg = new Message(service, size, data);
                
                if ( Message.getCheckSum(msg) == checksum ) {
                    ClientManager.MessageHandler(msg, checksum, this);
                }
                
            } while(true);
            
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
