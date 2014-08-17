/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package socket_multithread_server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leandro
 */
public class GerenteClientes implements Runnable {

   private static ArrayList<Client> listaClientes = new ArrayList<Client>();
   private static ArrayList<String> listaMensagens = new ArrayList<String>();

   /**
    *
    */
   public GerenteClientes() { }


   /**
    * Envia mensagem para todos os clientes ativos
    */
   public static void enviaMensagemClientes(String mensagem) {
      listaMensagens.add(mensagem);
   }

   public static void adicionaCliente(Client c) {
      listaClientes.add(c);
   }


   public void run() {

      while(true) {
         if (listaMensagens.size() >0) {
            // Retira a mensagem e envia para todos os clientes
            String mensagemAtual = listaMensagens.remove(0);
            for(int i=0; i < listaClientes.size(); i++) {
               Client clienteAtual = listaClientes.get(i);
               if (clienteAtual.getSocket().isConnected()) {
                  try {
                  clienteAtual.enviaMensagem(mensagemAtual);
                  } catch (IOException e) {
                    // Nada por enquanto
                  }
               }
            }
         }
         try {
            Thread.sleep(50);
         } catch (InterruptedException ex) {
            Logger.getLogger(GerenteClientes.class.getName()).log(Level.SEVERE, null, ex);
         }
      }

   }


}
