/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chatserver.business;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author pedro
 */
public class Server {
    
    private static int DEFAULT_PORT = 8885;
    
    private ServerSocket server;
    static ClientManager clientManager;
    static MessageHandler msgHandler;
    private Thread cM;
    private Thread msgHandlerThread;
    
    /**
     * This class implements a simple Server.
     * @param port
     * @throws IOException 
     */
    public Server(int port) throws IOException {
        
        this.server = new ServerSocket(port);
        
        msgHandler = new MessageHandler();
        this.msgHandlerThread = new Thread(msgHandler);
        
        clientManager = new ClientManager();
        this.cM = new Thread(clientManager);
        
        this.msgHandlerThread.setName("Message-Handler-Thread");
        msgHandlerThread.start();
        
        this.cM.setName("Client-Manager-Thread");
        this.cM.start();
        
        System.out.println("SERVER>" + server.getInetAddress().getHostAddress() + ":" + server.getLocalPort() + " (" + server.getInetAddress().getCanonicalHostName() + ")");
    }
    
    public Server() throws IOException {
        this(DEFAULT_PORT);
    }
    
    /**
     * Starts the server.
     * @throws IOException 
     */
    public void startServer() throws IOException {
        
        while( true ) {
            
            Client client = new Client(this.server.accept()); // Keeps listening for a new connection.
            Thread t = new Thread(client);
            t.start();
            
            Socket socket = client.getSocket();
            System.out.println("CLIENT>" + socket.getInetAddress().getHostAddress() + ":" + socket.getLocalPort() + " (" + socket.getInetAddress().getCanonicalHostName() + ")");
            
        }
        
    }
    
}
