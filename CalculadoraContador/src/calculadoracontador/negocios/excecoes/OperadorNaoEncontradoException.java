/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package calculadoracontador.negocios.excecoes;

/**
 *
 * @author Pedro
 */
public class OperadorNaoEncontradoException extends Exception {
    public OperadorNaoEncontradoException(String op) {
        super("Operador <" + op + "> não encontrado");
    }
    
}
