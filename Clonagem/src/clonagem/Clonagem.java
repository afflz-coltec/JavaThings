/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clonagem;

import clonagem.Ovelha.Dolly;
import clonagem.Santos.Santo;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Leandro
 */
public class Clonagem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Dolly ovelhaDolly = new Dolly();
        ArrayList<Dolly> listaOvelhas = new ArrayList<Dolly>();
        String[] opcoes = {"Clonar","Criar","Finalizar"};
        
        // Pergunta o que quer fazer
        forDeFora:
        for(;;) {
            int resposta = JOptionPane.showOptionDialog(null, 
                     "O que você deseja fazer ?", 
                     "Laboratório de Clonagem de Ovelhas", 
                     JOptionPane.YES_NO_CANCEL_OPTION, 
                     JOptionPane.QUESTION_MESSAGE, 
                     null, 
                     opcoes, 
                     opcoes[0]);
            
            switch (resposta) {
                // Clonar
                case JOptionPane.YES_OPTION:
                    try {
                        listaOvelhas.add((Dolly)ovelhaDolly.clone());
                    } catch (CloneNotSupportedException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                // Criar
                case JOptionPane.NO_OPTION:
                    listaOvelhas.add(new Dolly());
                    break;
                // Finalizar
                case JOptionPane.CLOSED_OPTION:
                case JOptionPane.CANCEL_OPTION:
                    break forDeFora;
            }
        }
        
        // Imprime todas as ovelhas criadas
        ovelhaDolly.imprimeOvelha();
        for(Dolly ovelha : listaOvelhas) {
            ovelha.imprimeOvelha();
        }
    }
}
