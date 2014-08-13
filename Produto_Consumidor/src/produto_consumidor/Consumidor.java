/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package produto_consumidor;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leandro
 */
public class Consumidor implements Runnable {
   
   private static int TEMPO_MAXIMO_DORMINDO_MS = 1000;
   private Buffer buffer;
   
   public Consumidor(Buffer buffer) {
      this.buffer = buffer;
   }

   @Override
   public void run() {
      
      for(;;) {
         int valor = buffer.retira();
         System.out.println("Consumiu: " + valor);
         System.out.flush();
         try {
            // Dorme por um tempo
            Thread.sleep((long)(Math.random() * TEMPO_MAXIMO_DORMINDO_MS));
         } catch (InterruptedException ex) {  }
      }
   }
}
