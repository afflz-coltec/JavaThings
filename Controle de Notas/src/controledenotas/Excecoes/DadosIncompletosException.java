/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controledenotas.Excecoes;

/**
 *
 * @author Leandro
 */
public class DadosIncompletosException extends Exception {
   
   public DadosIncompletosException() {
      super("Dados incompletos para persistir o registro.");
   }
   
}
