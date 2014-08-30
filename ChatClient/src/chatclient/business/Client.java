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

/**
 *
 * @author strudel
 */
public class Client implements Runnable {
    
    Socket socket;
    DataInputStream input;
    DataOutputStream output;
    
    String message = "";
    
    private static final int BYTES_PER_CHAR = 2;
    
    public Client(String ip, int port, String nick) throws IOException{
        
        this.socket = new Socket(ip,port);
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
        
        try {
            this.getConnected(nick);
        }
        catch ( IOException e ) {
            
        }
        
    }
    
    private void getConnected(String nick) throws IOException {
        
        byte[] msg = Message.getMsgAsByteVector(new Message(Services.HelloService.getByte(), nick.length()*BYTES_PER_CHAR, MsgUtils.stringToByteVector(nick)));
        
        for ( byte b : msg )
            this.output.writeByte(b);
        
        Message.printMessage(msg);
        
    }
    
    public void sendMessage(String message) throws IOException {
        this.output.writeChars(message);
    }
    
    public String getMessages() {
        return this.message;
    }

    @Override
    public void run() {
        
        char c;
        
        try {
            
            while ( true ) {

                do {
                    
                    c = this.input.readChar();
                    this.message = this.message + c;
                    
                } while ( c != '\n' );

                synchronized(this) {
                    notify();
                }
                
            }
            
        }
        catch ( IOException e ) {
            
        }
        
    }
    
}