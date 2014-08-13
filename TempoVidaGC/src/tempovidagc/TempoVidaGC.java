/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tempovidagc;

/**
 *
 * @author Leandro
 */
public class TempoVidaGC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Humano h1 = new Humano("Pessoa 01");
        Humano h2 = new Humano("Pessoa 02");
        Humano h3 = new Humano("The One");
        
        h3.conhecePessoa(h1);
        h1 = null;
        h2 = null;
        h3 = null;
        
        
        // Chamo o coletor de lixo
        System.gc();
        
        // Dorme um pouco para o coletor passar
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        

        // Quem H3 conhece ?
        h3 = Humano.testOcular;
        Humano[] lh = h3.obtemListaPessoasConhecidas();
        for(Humano h : lh) {
            System.out.println("H3 conhece: " + h.getNome());
        }
        
        // Ninguem conhece H3 mais
        h3 = null;
        Humano.testOcular = null;
        
        // Chamo o coletor de lixo
        System.gc();
        
        // Dorme um pouco para o coletor passar
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        
        // Pessoa 01 ainda está aqui ?
        System.out.println("Eu sou <" + lh[0].getNome() + ">");
        
        lh = null;

        // Chamo o coletor de lixo
        System.gc();
        
        // Dorme um pouco para o coletor passar
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        
        System.out.println("Fim do programa");
    }
}
