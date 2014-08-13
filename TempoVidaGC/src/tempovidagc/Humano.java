/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tempovidagc;

import java.util.ArrayList;

/**
 *
 * @author Leandro
 */
public class Humano implements Comparable<Humano>{
    
    // Nome da pessoa
    private String nome;
    // Lista de pessoas conhecidas
    ArrayList<Humano> listaPessoas;
    // Testemunha ocular
    public static Humano testOcular = null;
    
    /**
     * Humano.
     * @param nome Nome da pessoa.
     */
    public Humano(String nome) {
        this.nome = nome;
        this.listaPessoas = new ArrayList<>();
    }

    /**
     * Obtem o nome da pessoa.
     * @return Nome da pessoa.
     */
    public String getNome() {
        return nome;
    }
    
    /**
     * Passa a conhecer a pessoa.
     * @param h Pessoa a ser conhecida.
     */
    public void conhecePessoa(Humano h) {
        this.listaPessoas.add(h);
    }
    
    /**
     * Esquece da pessoa.
     * @param h Desafeto a ser esquecido.
     */
    public void esquecePessoa(Humano h) {
        this.listaPessoas.remove(h);
    }
    
    public Humano[] obtemListaPessoasConhecidas() {
        Humano[] lh = new Humano[this.listaPessoas.size()];
        return this.listaPessoas.toArray(lh);
    }
    
    /**
     * 
     */
    @Override
    protected void finalize() {
        try {
            super.finalize();
            System.out.println("Ahhh, eu <" + getNome() + "> morri.");
            if (this.nome.equalsIgnoreCase("The one")) {
                // Ressuscita
                testOcular = this;
                System.out.println("Eu <" + getNome() + "> voltei!");
            }
        } catch (Throwable e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public int compareTo(Humano o) {
        return this.nome.compareTo(o.getNome());
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Humano) {
            return compareTo((Humano)o) == 0;
        } else {
            return false;
        }
    }
}
