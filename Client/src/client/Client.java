/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author pedro
 */
public class Client {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Socket client = new Socket("127.0.0.1",12345);
        System.out.println("Conectado");
        
        Scanner kb = new Scanner(System.in);
        
        PrintStream op = new PrintStream(client.getOutputStream());
        
        while ( kb.hasNextLine() ) {
            op.println(kb.nextLine());
        }
        
        client.close();
        kb.close();
        op.close();
    }
    
}
