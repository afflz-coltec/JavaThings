/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

/**
 *
 * @author Leandro
 */
public class ObjetoRunnable implements Runnable {
    
   // Mensagem a ser impressa na thread
   private final String MENSAGEM;
   private final boolean DORMIR;
   private final int TEMPO_MAX_MS;
   private final boolean LOOP;
   
   public ObjetoRunnable(String mensagem) {
      this(mensagem,false,0,true);
   }
   
   public ObjetoRunnable(String mensagem, int tempoMaxMs) {
      this(mensagem,true,tempoMaxMs,true);
   }
   
   public ObjetoRunnable(String mensagem, boolean loop) {
      this(mensagem,false,0,loop);
   }
   
   public ObjetoRunnable(String mensagem, int tempoMaxMs, boolean loop) {
      this(mensagem,true,tempoMaxMs,loop);
   }
   

   private ObjetoRunnable(String mensagem, boolean dormir, int tempoMaxMs, boolean loop) {
      this.MENSAGEM = mensagem;
      this.TEMPO_MAX_MS = tempoMaxMs;
      this.DORMIR = dormir;
      this.LOOP = loop;
   }

   @Override
   public void run() {
      try {
         do {
            // Imprime a mensagem
            if (this.MENSAGEM != null) System.out.println(MENSAGEM);
            // Dorme por um tempo aleatório até o máximo de TEMPO_MAX_MS
            if (this.DORMIR) Thread.sleep((long)(Math.random() * TEMPO_MAX_MS));
         } while(LOOP);
      } catch (InterruptedException ex) {
         // Faço nada, só encerro a thread
      }
   }
}
