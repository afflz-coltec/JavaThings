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
public class NewFiesta extends Carro {
    
    private static Montadora montadora = Montadora.MONTADORA_UNIVERSAL;
    
    public NewFiesta() {
        super(montadora);
    }
    
    @Override
    public String getModelo() {
        return "Fiesta";
    }
    
    @Override
    protected String getModeloChassi() {
        return "FISTA";
    }
    
    public static void associaMontadora(Montadora m) {
        montadora = m;
    }
    
}
