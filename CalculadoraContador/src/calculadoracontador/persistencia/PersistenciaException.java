/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package calculadoracontador.persistencia;

/**
 *
 * @author Pedro
 */
public class PersistenciaException extends Exception{
    
    public PersistenciaException(String erro) {
        super("Erro ao realizar: <" + erro + ">");
    }
    
}
