/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chatzin;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author pedro
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        ServerSocket server = new ServerSocket(12345);
        
        System.out.println("Porta aberta");
        
        Socket client = server.accept();
        
        System.out.println("Nova conexao com cliente " + client.getInetAddress().getHostAddress());
        
        Scanner s = new Scanner(client.getInputStream());
        
        while (s.hasNextLine() ) {
            System.out.println(s.nextLine());
        }
        
        s.close();
        server.close();
        client.close();
        
    }
    
}
