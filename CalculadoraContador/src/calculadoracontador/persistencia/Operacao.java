/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package calculadoracontador.persistencia;
import calculadoracontador.negocios.Calculadora;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

/**
 *
 * @author Pedro
 */
public class Operacao {
    
    private double opA;
    private Calculadora.Operacoes operacao;
    
    /**
     * Objeto que registra uma operação na calculadora de contador
     * @param a Operando A
     * @param op Operação
     */
    public Operacao(double a, Calculadora.Operacoes op) {
        this.opA = a;
        this.operacao = op;
    }
    
    public void persiste() throws PersistenciaException {
        OutputStream conexao = Conexao.obtemConexao();
        
        String info = String.format(Locale.US,"%s %.4f\n", Calculadora.obtemStringOperador(operacao), opA);
        
        try {
            conexao.write(info.getBytes());
        } catch (IOException e) {
            throw new PersistenciaException("Escrever dados");
        }
    }
    
    public double getOpA() {
        return this.opA;
    }
    
    public Calculadora.Operacoes getOperador() {
        return this.operacao;
    }
    
}
