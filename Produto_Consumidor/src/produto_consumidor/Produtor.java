/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package produto_consumidor;

/**
 *
 * @author Leandro
 */
public class Produtor implements Runnable {

   private static int VALOR_MAXIMO_PRODUZIDO = 1000;
   private static int TEMPO_MAXIMO_DORMINDO_MS = 100;
   private Buffer buffer;
   
   public Produtor(Buffer buffer) {
      this.buffer = buffer;
   }

   @Override
   public void run() {
      
      for(;;) {
         // Produz um número aleatório
         int prod = (int)(Math.random() * VALOR_MAXIMO_PRODUZIDO);
      
         System.out.println("Produziu: " + prod);
         System.out.flush();
         buffer.insere(prod);
         try {
            // Dorme por um tempo
            Thread.sleep((long)(Math.random() * TEMPO_MAXIMO_DORMINDO_MS));
         } catch (InterruptedException ex) {  }
      }
   }
}
