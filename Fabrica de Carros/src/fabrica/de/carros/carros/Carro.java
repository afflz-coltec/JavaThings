/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fabrica.de.carros.carros;

import fabrica.de.carros.montadoras.Montadora;

/**
 *
 * @author Leandro
 */
public class Carro {
    
    // Modelo Padrão
    private static final String MODELO_PADRAO = "Padrão";
    private static final String MODELO_PADRAO_CHASSI = "ABCDE";
    
    // Modelo e Chassi
    private final String chassi;
    
    public Carro() {
        this(Montadora.MONTADORA_UNIVERSAL);
    }
    
    public Carro(Montadora montadora) {
        this.chassi = montadora.obtemNovoChassi();
    }
    
    protected String getModelo() {
        return MODELO_PADRAO;
    }
    
    protected String getModeloChassi() {
        return MODELO_PADRAO_CHASSI;
    }
    
    public final String getChassi() {
        return this.chassi.replace("-----", getModeloChassi());
    }
    
    public void imprimeCarro() {
        System.out.println("<" + getModelo() + "," + getChassi() + ">");
    }
}
