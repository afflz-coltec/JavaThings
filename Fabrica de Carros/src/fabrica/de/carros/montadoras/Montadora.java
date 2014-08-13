/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fabrica.de.carros.montadoras;

import java.util.ArrayList;

/**
 *
 * @author Leandro
 */
public class Montadora {
    
    // Nome da montadora universal
    public static final Montadora MONTADORA_UNIVERSAL;
    
    // Lista de montadoras conhecidas
    private static final ArrayList<Montadora> listaMontadoras = new ArrayList<>();
    
    static {
        MONTADORA_UNIVERSAL = new Montadora("Universal", "WRD");
        listaMontadoras.add(MONTADORA_UNIVERSAL);
    }
    
    // Membros
    private final String nomeMontadora;
    private final String codigo;
    private int indiceChassi;
    
    private Montadora(String nome, String prefixo) {
        this.nomeMontadora = nome;
        this.codigo = prefixo;
        this.indiceChassi = 0;
    }
    
    public String getNome() {
        return this.nomeMontadora;
    }
    
    public String getCodigo() {
        return this.codigo;
    }
    
    public int getIndiceProximoChassi() {
        return this.indiceChassi;
    }
    
    private int obtemIndice() {
        return this.indiceChassi++;
    }
    
    @Override
    public boolean equals(Object o) {
        // Se não for montadora, então certamente não é igual
        if (! (o instanceof Montadora)) return false;
        
        // Duas montadoras são consideradas a mesma se possuem o mesmo codigo
        return this.codigo.equalsIgnoreCase(((Montadora)o).codigo);
    }
    
    public static Montadora criaMontadora(String nome, String codigo) {
        // Cria uma montadora
        Montadora m = new Montadora(nome, codigo);
        // Verifica se ela já está na lista
        boolean existe = listaMontadoras.contains(m);
        if (existe) {
            // Se existe, retorna o que já existe
            return listaMontadoras.get(listaMontadoras.indexOf(m));
        } else {
            // Adiciona na lista e retorna
            listaMontadoras.add(m);
            return m;
        }
    }
    
    public String obtemNovoChassi() {
        return String.format("%s-----%05d", getCodigo(),obtemIndice());
    }
    
    public static Montadora[] obtemListaMontadoras() {
        Montadora[] lstMontadoras = new Montadora[listaMontadoras.size()];
        return listaMontadoras.toArray(lstMontadoras);
    }
    
    public static void imprimeListaMontadoras() {
        Montadora[] lstMontadoras = obtemListaMontadoras();
        for(Montadora m : lstMontadoras) {
            System.out.print("<" + m.getNome() + "," + m.getCodigo() + ">");
        }
        System.out.println("\n");
    }
            
}
