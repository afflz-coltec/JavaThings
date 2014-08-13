/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package produto_consumidor;

/**
 *
 * @author Leandro
 */
public class Main {

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
      // TODO code application logic here
      
      Buffer b = new Buffer();
      Produtor p = new Produtor(b);
      Produtor p2 = new Produtor(b);
      Consumidor c = new Consumidor(b);
      
      Thread tp = new Thread(p);
      Thread tp2 = new Thread(p2);
      Thread tc = new Thread(c);
      
      tp.start();
      tp2.start();
      tc.start();
      
   }
}
