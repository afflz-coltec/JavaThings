/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package calculadoracontador.persistencia;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pedro
 */
public class Conexao {
    
    
    private static OutputStream arquivo;
    
    /**
     * Obtem a conexão para escrever o log da calculadora.
     * @return Stream utilizado para escrever os dados.
     * @throws PersistenciaException Exceção levantada quando não conseguir 
     * obter o stream.
     */
    public static OutputStream obtemConexao() throws PersistenciaException {
        
        if (arquivo == null) {
            try {
                arquivo = new FileOutputStream("fita.txt");
            } catch (FileNotFoundException ex) {
                throw new PersistenciaException("Abrir arquivo para escrita");
            }
        }
        
        return arquivo;
    }
    
    public static void fechaConexao() {
        if (arquivo != null) {
            try {
                arquivo.close();
            } catch (IOException e) {
                // Ignorando o erro
            }
        }
    }
    
}
