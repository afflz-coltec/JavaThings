/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbcexample;

import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author strudel
 */
public class Form {
    
    private static JPanel Painel     = new JPanel();
    private static JFrame Formulario = new JFrame();
    
    public static void main( String[] args )
    {        
        //--[ TERMINAR A EXECUCAO SE O FORMULARIO FOR FECHADO ]--\\
        Formulario.setDefaultCloseOperation( Formulario.EXIT_ON_CLOSE ); 
         
        //--[ SETA O TITULO  DO FORMULARIO ]--\\
        Formulario.setTitle( "Posicionando manualmente os componentes no Formulário" );         
         
        //--[ SETA O TAMANHO DO FORUMLARIO ]--\\
        Formulario.setSize( 490 , 270 );             
         
        //--[ CENTRALIZA O FORMULARIO ]--\\
        Formulario.setLocationRelativeTo( null );    
         
        //--[ DESLIGANDO O GERENCIADOR DE LAYOUT ]--\\
        Painel.setLayout( null );         
         
        //--[ ADICIONA O PAINEL AO FORMULARIO ]--\\        
        Formulario.add( Painel );                                    
         
        //--[ CRIANDO OS LABEL'S ]--\\
        JLabel Label1 = new JLabel( "Label 1" );
        JLabel Label2 = new JLabel( "Label 2" );        
        JLabel Label3 = new JLabel( "Label 3 com borda, centralizado e dimensão de 460px por 100px" );
        JLabel Label4 = new JLabel( "Botão na linha 190 e coluna 220: " );
        JLabel Label5 = new JLabel( "Foi útil?" );
         
        //--[ CRIA UMA BORDA NO JLABEL ]--\\
        Label3.setBorder( BorderFactory.createTitledBorder( "" ) ) ;
         
        //--[ SETA O ALINHAMENTO CENTRAL ]--\\
        Label3.setHorizontalAlignment( SwingConstants.CENTER );
         
        //--[ CRIANDO OS CAMPOS DE TEXTO ]--\\
        JTextField Text1 = new JTextField( "JTextField adicionado manualmente" );
        JTextField Text2 = new JTextField( "Pode-se colocar o componente no lugar exato!" );
         
        //--[ CRIANDO A COMBO BOX ]--\\
        JComboBox Combo  = new JComboBox();
         
        //--[ ADICIONANDO ITENS NO COMBO BOX ]--\\
        Combo.addItem( "Sim" );
        Combo.addItem( "Não" );
         
        //--[ CRIANDO O BOTAO ]--\\
        JButton Botao = new JButton( "OK" );                
         
        //--[ ADICIONANDO OS COMPONENTES NO PAINEL DO FORMULARIO ]--\\
        Adiciona( Label1 ,  10 ,  10 ,  70 ,  25 ) ;
        Adiciona( Text1  ,  80 ,  10 , 230 ,  25 ) ;
         
        Adiciona( Label2 ,  10 ,  40 ,  70 ,  25 ) ; 
        Adiciona( Text2  ,  80 ,  40 , 390 ,  25 ) ;         
         
        Adiciona( Label3 ,  10 ,  70 , 460 , 100 ) ;         
         
        Adiciona( Label4 ,  10 , 190 , 250 ,  30 ) ; 
        Adiciona( Botao  , 220 , 190 ,  80 ,  30 ) ;         
         
        Adiciona( Label5 , 320 ,  10 ,  60 ,  25 ) ; 
        Adiciona( Combo  , 390 ,  10 ,  80 ,  25 ) ;         
         
        Formulario.setVisible( true );
    }    
     
    //--[ FUNCAO PARA ADICIONAR COMPONENTES NO PAINEL DO FORMULARIO ]--\\
    private static void Adiciona( Component Componente , int nColuna , int nLinha , int nLargura , int nAltura )  
    {
        //--[ ADICIONA O COMPONENTE NO PAINEL ]--\\
        Painel.add( Componente ) ;                      
         
        //--[ SETA A POSICAO EXATA DO COMPONENTE ]--\\
        Componente.setBounds( nColuna , nLinha , nLargura , nAltura );
    }   
    
}
