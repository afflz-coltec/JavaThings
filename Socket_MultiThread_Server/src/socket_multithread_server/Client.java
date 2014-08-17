/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package socket_multithread_server;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Leandro
 */
public class Client implements Runnable {

   private Socket socket;
   private DataInputStream input;
   private DataOutputStream output;
   private int clientID;

   private static int lastClientID = 0;

   /**
    * Cria um objeto Client que representa uma conexão com um cliente do servidor.
    * @param socket Socket correspondente à conexão com o cliente.
    */
   public Client(Socket socket) throws IOException {
      this.socket = socket;
      this.input = new DataInputStream(this.socket.getInputStream());
      this.output = new DataOutputStream(this.socket.getOutputStream());
      this.clientID = ++lastClientID;
      GerenteClientes.adicionaCliente(this);
   }

   public void enviaMensagem(String mens) throws IOException {
      this.output.writeChars(mens);
   }

   /**
    * Obtem o socket utilizado na conexão com o cliente.
    * @return Socket.
    */
   public Socket getSocket() { return this.socket; }

   /**
    * Método chamada para iniciar o tratamento da thread.
    */
   public void run() {

      // Mensagem de boas vindas
      System.out.println("WELCOME> Cliente ID " + this.clientID + " entrou. (" + socket.getInetAddress().getHostAddress() + ")");

      while(true) {

         // Recebe uma mensagem
         String mensagem = "";
         char caractere;
         do {

            try {
            caractere = this.input.readChar();
            } catch (IOException e) {
               System.out.println("CLOSE> Cliente ID " + this.clientID + " saiu. ("   + socket.getInetAddress().getHostAddress() + ")");
               return;
            }
            mensagem = mensagem + String.valueOf(caractere);

         } while (caractere != '\n');

         // Envia mensagem par atodos os clientes
         GerenteClientes.enviaMensagemClientes("MESSAGE FROM " + this.clientID + "> " + mensagem);

         // Imprime a mensagem na tela
         System.out.print("MESSAGE FROM " + this.clientID + "> " + mensagem );
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
