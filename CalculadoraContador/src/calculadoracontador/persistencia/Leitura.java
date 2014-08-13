/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadoracontador.persistencia;

import calculadoracontador.negocios.Calculadora;
import static calculadoracontador.negocios.Calculadora.obtemOperador;
import calculadoracontador.negocios.excecoes.OperadorNaoEncontradoException;
import calculadoracontador.negocios.excecoes.Operando1NaoDefinidoException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe para leitura do arquivo de log.
 * @author Pedro
 */
public class Leitura {

    /**
     * Método para obter a lista de strings de operações salvas no arquivo de log.
     * @return ArrayList de Strings.
     * @throws PersistenciaException 
     */
    public static ArrayList obtemListaOperacoes() throws PersistenciaException{
        
        ArrayList<String> opList = new ArrayList<>();

        try {
            BufferedReader listaOp = new BufferedReader(new FileReader("fita.txt"));
            String line = null;
            
            while ((line = listaOp.readLine()) != null) {
                opList.add(line); // Add cada linha do arquivo em um ArrayList de arquivos.
            }
        } catch (IOException e) {
            throw new PersistenciaException("Leitura de Arquivo");
        }
        return opList;
    }
    
    /**
     * Método para decodificar string de operação e adiciona-las em um ArrayList.
     * @return ArrayList de objetos Operacao.
     */
    public static ArrayList decodificaArquivo() {
        
        ArrayList<String> StringOpList = new ArrayList<>(); // ArrayList de strings do arquivo
        ArrayList<Operacao> LogList = new ArrayList<>(); // ArrayList para colocar as operações restauradas
        
        try {
            StringOpList = Leitura.obtemListaOperacoes();
            
            for ( String s : StringOpList ) {
                
                if ( s.equalsIgnoreCase("-----------") ) {
                    LogList.add(null); // Add um campo null na lista de arquivos caso haja uma barrinha na linha do arquivo.
                }
                else { // Pega a string da linha e splita ela para adicionar uma nova operação à lista utilizando os métodos de operações.
                    String[] Line = s.split(" ");

                    double Op1 = Double.parseDouble(Line[1]);
                    Calculadora.Operacoes op = obtemOperador(Line[0]);
                    
                    LogList.add(new Operacao(Op1, op));
                }
                
            }
            
        }
        catch ( PersistenciaException e ) {

        }
        catch ( OperadorNaoEncontradoException e ) {
            
        }
        
        return LogList;
    }

}
