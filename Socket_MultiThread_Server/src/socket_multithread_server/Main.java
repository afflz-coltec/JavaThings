/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package socket_multithread_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Leandro
 */
public class Main {


   private static final int SERVER_PORT = 8885;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

       try {

         // Iniciando o servidor
         ServerSocket server = new ServerSocket(SERVER_PORT);
         System.out.println("SERVIDOR>" + server.getInetAddress().getHostAddress() + ":" + server.getLocalPort() + " (" + server.getInetAddress().getCanonicalHostName() + ")");

         GerenteClientes gerente = new GerenteClientes();
         Thread threadGerente = new Thread(gerente);
         threadGerente.start();

         while(true) {

            // Iniciando o cliente
            Client cliente = new Client((server.accept()));
            Socket socket = cliente.getSocket();
            System.out.println("CLIENTE>" + socket.getInetAddress().getHostAddress() + ":" + socket.getLocalPort() + " (" + socket.getInetAddress().getCanonicalHostName() + ")");

            // Disparando uma thread para aceitar uma nova conexão
            Thread t = new Thread(cliente);
            t.start();
         }

       } catch (IOException e) {
          System.out.println(e);
       }

    }
}
