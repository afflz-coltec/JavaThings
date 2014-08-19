/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chat_server.server;

import chat_server.client.Client;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author pedro
 */
public class Server {
    
    private ServerSocket server;
    private ClientManager clientManager;
    private Thread cM;
    
    public Server(int port) throws IOException{
        
        this.server = new ServerSocket(port);
        System.out.println("SERVER>" + server.getInetAddress().getHostAddress() + ":" + server.getLocalPort() + " (" + server.getInetAddress().getCanonicalHostName() + ")");
        
    }
    
    public void startServer() throws IOException {
        
        this.clientManager = new ClientManager();
        this.cM = new Thread(clientManager);
        
        while ( true ) {
            
            Client client = new Client(this.server.accept());
            Socket socket = client.getSocket();
            
            System.out.println("CLIENTE>" + socket.getInetAddress().getHostAddress() + ":" + socket.getLocalPort() + " (" + socket.getInetAddress().getCanonicalHostName() + ")");
            
            Thread t = new Thread(client);
            t.start();
            
        }
        
    }
    
}
