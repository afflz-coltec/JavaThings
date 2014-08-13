/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package calculadoracontador.negocios;

import calculadoracontador.negocios.excecoes.OperadorNaoEncontradoException;
import calculadoracontador.negocios.excecoes.Operando1NaoDefinidoException;
import calculadoracontador.persistencia.Conexao;
import calculadoracontador.persistencia.Leitura;
import calculadoracontador.persistencia.Operacao;
import calculadoracontador.persistencia.PersistenciaException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

/**
 *
 * @author Pedro
 */
public class Calculadora {
    
    // Operandos
    private double op1;
    // Já recebeu o primeiro operando ?
    private boolean primeiroOpOk = false;
    
    //Lista de Operações
    private ArrayList<Operacao> OpList = new ArrayList<>();
    
    //Lista de Operações a serem restauradas
    private ArrayList<Operacao> OldOpList;
    
    // Operadores permitidos
    public enum Operacoes {
        eAdicao,
        eSubtracao,
        eMultiplicacao,
        eDivisao,
        eNada // Tipo eNada para determinar um primeiro operando ou resultado de uma operação
    };
    
    /**
     * Strings de cada operação
     */
    private static final String CONSTANTES_OPERACOES[] = {
        "+","-","*","/","#" // o símbolo "#" indica um primeiro operando ou um resultado de operação
    };
    
    /**
     * Construtor de uma Calculadora de Contador.
     */
    public Calculadora() {
        
    }
    
    /**
     * Define o primeiro operando.
     * @param op Valor do operando.
     */
    public void setOperando1(double op) {
        this.op1 = op;
        this.primeiroOpOk = true;
        OpList.add(new Operacao(op1, Operacoes.eNada)); // Add o primeiro operando a lista de operações
    }
    
    public double realizaOperacao(Operacoes operacao, double op2) throws Operando1NaoDefinidoException{
        
        // Só é possível realizar operação se o primeiro operando já estiver definido
        if (!this.primeiroOpOk) {
            throw new Operando1NaoDefinidoException();
        }
        
        // Se eu cheguei aqui, é porque eu já tenho o primeiro operando definido
        
        double resultado;
        switch(operacao) {
            
            case eAdicao:
                resultado = this.op1 + op2;
                break;
            case eSubtracao:
                resultado = this.op1 - op2;
                break;
            case eMultiplicacao:
                resultado = this.op1 * op2;
                break;
            case eDivisao:
                resultado = this.op1 / op2;
                break;
            default:
                // Nunca deveria entrar aqui :/
                throw new IllegalArgumentException("Operador não definido");
        }
        
        OpList.add(new Operacao(op2, operacao)); // Add o segundo operando à lista.
        
        imprimeOperacao(operacao, op1, op2, resultado);
        
        this.op1 = resultado;
        return resultado;
  
    }
    
    /**
     * Retorna valor do primeiro operando.
     * @return Operando 1
     */
    public double getOp1() {
        return op1;
    }
    
    /**
     * Obtem o operador baseado no texto do operador.
     * @param operador Texto do operador
     * @return Operador equivalente.
     * @throws OperadorNaoEncontradoException Exceção levantada caso o texto do
     * operador não seja equivalente a nenhum operador reconhecido.
     */
    public static Operacoes obtemOperador(String operador) throws OperadorNaoEncontradoException{
        
        for(int i=0; i < CONSTANTES_OPERACOES.length; i++) {
            if (CONSTANTES_OPERACOES[i].equalsIgnoreCase(operador)) {
                return Operacoes.values()[i];
            }
        }
        
        // Se chegou até aqui é porque não encontrou nenhum operador equivalente
        throw new OperadorNaoEncontradoException(operador);
        
    }
    
    /**
     * Obtem a string correspondente a operação.
     * @param o Operação.
     * @return String da operação.
     */
    public static String obtemStringOperador(Calculadora.Operacoes o) {
        return CONSTANTES_OPERACOES[o.ordinal()];
    }
    
    /**
     * Método de depuração
     * @param operacao
     * @param v1
     * @param v2
     * @param resultado 
     */
    private void imprimeOperacao(Operacoes operacao, double v1, double v2, double resultado) {
        
        System.out.println(String.format("=> %.2f %s %.2f = %.2f",
                                         v1,
                                         CONSTANTES_OPERACOES[operacao.ordinal()],
                                         v2,
                                         resultado));
    }
    
    /**
     * Método para adicionar um campo nulo na lista de operações para representar a barrinha.
     */
    public void SalvaResultadoDoIgual () {
        OpList.add(null);
        OpList.add(new Operacao(op1, Operacoes.eNada));
    }
    
    /**
     * Método para salvar as operções no arquivo.
     */
    public void SalvaOperacoes() {
        
        try {
            if ( OpList.isEmpty() ) {
                Conexao.obtemConexao().write("".getBytes()); // Quando a lista estiver vazia, apaga o que há no arquivo.
            }
            else {
                for ( Operacao op : OpList ) { // para cada operação na lista...
                    if ( op != null ) 
                        op.persiste(); // se não for null persiste
                    else
                        Conexao.obtemConexao().write("-----------\n".getBytes()); // se for, printa uma barra
                }
            }
        }
        catch ( PersistenciaException e ) {
            e.getMessage();
        }
        catch ( IOException e ) {
            
        }
        
        Conexao.fechaConexao();
        
    }
   
    /**
     * Método que restaura as operações antigas realizando as operações.
     * @return ArrayList de Operacoes
     */
    private ArrayList restauraEstado() {
        
        try {
            
            OldOpList = Leitura.decodificaArquivo();
            
            for ( Operacao op : OldOpList ) {
                
                if ( op == null ) {
                    this.OpList.add(null);
                }
                else {
                    if ( op.getOperador() == Calculadora.Operacoes.eNada ) 
                        setOperando1(op.getOpA());
                    else 
                        realizaOperacao(op.getOperador(), op.getOpA());
                
                }
                
            }
            
        }
        catch ( Operando1NaoDefinidoException e ) {
            
        }
        
        return this.OpList;
        
        // Momento em que eu lembrei que quem deve entender e decodificar o arquivo de log é a camada de persistência...
        
//        try {
//            OldOpList = Leitura.obtemListaOperacoes();
//            
//            for ( String s : OldOpList ) {
//                
//                if ( s.equalsIgnoreCase("-----------") ) {
//                    this.OpList.add(null); // Add um campo null na lista de arquivos caso haja uma barrinha na linha do arquivo.
//                }
//                else { // Pega a string da linha e splita ela para adicionar uma nova operação à lista utilizando os métodos de operações.
//                    String[] Line = s.split(" ");
//
//                    double Op1 = Double.parseDouble(Line[1]);
//                    Operacoes op = obtemOperador(Line[0]);
//
//                    if ( op == Operacoes.eNada ) 
//                        setOperando1(Op1);
//                    else 
//                        realizaOperacao(op, Op1);
//                }
//                
//            }
//            
//        }
//        catch ( PersistenciaException e ) {
//
//        }
//        catch ( OperadorNaoEncontradoException e ) {
//            
//        }
//        catch ( Operando1NaoDefinidoException e ) {
//            
//        }
    }
    
    /**
     * Método para obter o Log restaurado à partir do arquivo fita.txt.
     * @return 
     */
    public String getLog() {
        // ArrayList que contem a lista de operaçoes a serem restauradas.
        ArrayList<Operacao> OperationList = restauraEstado();
        
        // Variavel de controle para determinar quando sera necessario printar uma linha vazia
        // apos o resultado de uma operaçao.
        boolean NewLine = false;
        
        // Variavel para determinar se o numero a ser printado eh resultado de alguma operação
        boolean isResult = false;
        
        // String para retorno na camada de apresentação
        String Log = "";
        
        for ( Operacao op : OperationList ) {
            
            if ( op != null ) {
                
                switch (op.getOperador()) {
                    case eNada:
                        if ( isResult == true ) { // Se for um resultado, imprime o resultado e seta a variavel NewLine como true e a isResult como false.
                            Log += String.format(Locale.US,"%.4f",op.getOpA()) + "   \n";
                            NewLine = true;
                            isResult = false;
                        }
                        else if ( NewLine == true ) { // Imprime uma nova linha quando após um resultado.
                            Log += "\n";
                            Log += String.format(Locale.US,"%.4f",op.getOpA()) + "   \n";
                        }
                        else { // Imprime o primeiro número de todos.
                            Log += String.format(Locale.US,"%.4f",op.getOpA()) + "   \n";
                        }
                        break;
                       
                    case eAdicao:
                    case eSubtracao:
                    case eMultiplicacao:
                    case eDivisao:
                        Log += String.format(Locale.US,"%.4f",op.getOpA()) + " " + Calculadora.obtemStringOperador(op.getOperador()) + " \n";
                        // Imprime o operando seguido de seu operador.
                        break;
                        
                    default:
                        break;
                    
                }
                
            }
            else { // Se o campo no ArrayList for nulo, imprime a barrinha e seta a variável isResult como true.
                Log +=  "----------- \n";
                isResult = true;
            }
            
        }
        
        return Log;
    }

}