/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package produto_consumidor;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leandro
 */
public class Buffer {
   
   // Tamanho padrão de um objeto buffer sem parâmetro
   private static final int BUFFER_TAMANHO_MAXIMO_PADRAO = 10;

   private ArrayList<Integer> buffer;
   private int tamanhoMaximo = BUFFER_TAMANHO_MAXIMO_PADRAO;
   
   public Buffer() {
      this(BUFFER_TAMANHO_MAXIMO_PADRAO);
   }
   
   public Buffer(int tamanho) {
      
      // Não permito buffer com tamanho zero ou valor negativo
      if (tamanho <= 0) { throw new IllegalArgumentException(); }
      
      this.buffer = new ArrayList<Integer>(tamanho);
      this.tamanhoMaximo = tamanho;
   }
   
   /**
    * Método que retira um elemento do buffer.
    * @return Elemento retirado.
    */
   public synchronized int retira() {
      
      if (buffer.size() == 0) {
         try {
            // Não há o que retirar, então espera o produtor colocar alguma coisa
            System.out.println("O buffer está vazio");
            System.out.flush();
            wait();
         } catch (InterruptedException ex) { }
      }
      
      // Retira um e avisa ao produtor que liberou espaço
      int valorRetirado = buffer.remove(0).intValue();
      System.out.println("Removeu:" + valorRetirado);
      System.out.flush();
      notify();
      
      return valorRetirado;
   }
   
   public synchronized void insere(int elemento) {
      
      if (buffer.size() == tamanhoMaximo) {
         try {
            // Buffer está cheio, espera consumidor liberar espaço
            System.out.println("O buffer está cheio");
            System.out.flush();
            wait();
         } catch (InterruptedException ex) { }
      }

      // Insere um e avisa ao consumidor que produziu um
      buffer.add(elemento);
      System.out.println("Inseriu: " + elemento);
      System.out.flush();
      notify();
   }
   
   
}
