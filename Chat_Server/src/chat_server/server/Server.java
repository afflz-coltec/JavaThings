/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chat_server.server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author pedro
 */
public class Server {
    
    private ServerSocket server;
    
    public Server(int port) throws IOException{
        
        this.server = new ServerSocket(port);
        
    }
    
}
