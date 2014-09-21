/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controledenotas.Excecoes;

/**
 *
 * @author Leandro
 */
public class RegistroNaoEncontradoException extends Exception {
   
   public RegistroNaoEncontradoException() {
      super("Registro nao encontrado.");
   }
   
}
