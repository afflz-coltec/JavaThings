/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.business;

import client.utils.MessageUtils;
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
    
    public Client(String ip, int port) throws IOException{
        
        this.socket = new Socket(ip,port);
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
        
    }
    
    public void sendMessage(String message) throws IOException {
        this.output.writeByte(0x01);
        byte[] size = MessageUtils.integerToByteVector(message.length()*2);
        for ( byte b : size ) {
            this.output.writeByte(b);
        }
        int tamanho = MessageUtils.byteVectorToInteger(size);
        byte[] data = MessageUtils.stringToByteVector(message);
        for ( byte b : data ) {
            this.output.writeByte(b);
        }
        String mens = MessageUtils.byteVectorToString(data);
    }
    
    public static void main(String[] args) {
        try {
        Client client = new Client("10.0.0.23", 8885);
        }
        catch ( IOException e ) {
            
        }
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