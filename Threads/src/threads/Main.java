/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import java.io.IOException;

/**
 *
 * @author Leandro
 */
public class Main {
   
   public static void threadUmaExecucao() throws IOException {
       // Cria os objetos
       ObjetoRunnable objA = new ObjetoRunnable("Objeto A", false);
       ObjetoRunnable objB = new ObjetoRunnable("Objeto B", false);
       // Cria as threas para cada objeto       
       Thread thread1 = new Thread(objA);
       Thread thread2 = new Thread(objB);
       // Inicia as thread
       thread1.start();
       thread2.start();
   }   
   
   public static void threadEmLoop() throws IOException {
       // Cria os objetos
       ObjetoRunnable objA = new ObjetoRunnable("Objeto A", 2000);
       ObjetoRunnable objB = new ObjetoRunnable("Objeto B", 2000);
       // Cria as threas para cada objeto       
       Thread thread1 = new Thread(objA);
       Thread thread2 = new Thread(objB);
       // Inicia as thread
       thread1.start();
       thread2.start();
       // Aguarda <ENTER>
       System.in.read();
       // Finaliza as outras thread
       thread1.interrupt();
       thread2.interrupt();
   }
   
   public static void threadEmLoopSemSleep() throws IOException {
       // Cria os objetos
       ObjetoRunnable objA = new ObjetoRunnable("Objeto A", 2000);
       ObjetoRunnable objB = new ObjetoRunnable("Objeto B");
       // Cria as threas para cada objeto       
       Thread thread1 = new Thread(objA);
       Thread thread2 = new Thread(objB);
       // Inicia as thread
       thread1.start();
       thread2.start();
       // Aguarda <ENTER>
       System.in.read();
       // Finaliza as outras thread
       thread1.interrupt();
       thread2.interrupt();
   }   

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
       
       // Primeiro Exemplo
       // Sem controle da inicialização das threads não é possível determinar
       // se a thread do objA será iniciada antes do objB, mesmo entando antes.
       //threadUmaExecucao();
       
       // Segundo Exemplo
       // Ambas as threads executando e dormindo por um tempo aleatório entre
       // 0 e 2000ms.
       //threadEmLoop();
       
       // Terceiro Exemplo
       // Ambas as threads executando, mas uma não dorme. Isso faz com que 
       // o processador seja quase que todo tomado para ela.       
       //threadEmLoopSemSleep();
    }
}
