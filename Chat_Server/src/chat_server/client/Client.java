/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chat_server.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author pedro
 */
public class Client implements Runnable {

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private int ClientID;
    
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
        
    }
    
    public Socket getSocket() {
        return this.socket;
    }
    
    @Override
    public void run() {
        
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
