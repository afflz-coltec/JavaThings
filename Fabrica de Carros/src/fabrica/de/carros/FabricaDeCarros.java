/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fabrica.de.carros;

import fabrica.de.carros.carros.Carro;
import fabrica.de.carros.carros.NewFiesta;
import fabrica.de.carros.montadoras.Montadora;

/**
 *
 * @author Leandro
 */
public class FabricaDeCarros {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Montadora.imprimeListaMontadoras();
        Montadora m = Montadora.criaMontadora("Fiat", "FIA");
        Montadora.imprimeListaMontadoras();
        Montadora n = Montadora.criaMontadora("Fiat", "FIA");
        Montadora.imprimeListaMontadoras();
        
        Carro c = new Carro();
        c.imprimeCarro();
        Carro d = new Carro();
        d.imprimeCarro();
        Carro e = new Carro(m);
        e.imprimeCarro();
        
        NewFiesta.associaMontadora(m);
        Carro f = new NewFiesta();
        f.imprimeCarro();
        
    }
    
}
