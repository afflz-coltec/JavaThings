/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controledenotas;

import controledenotas.Excecoes.RegistroNaoEncontradoException;
import controledenotas.persistencia.Aluno;
import controledenotas.persistencia.Conexao;
import controledenotas.persistencia.Disciplina;
import controledenotas.persistencia.Turma;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leandro
 */
public class ControleDeNotas {

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
      try {
         
         ArrayList<Aluno> listaAlunos =  Aluno.obtemListaAlunos();
         for (Aluno tmpAluno : listaAlunos) {
            System.out.println("--> Aluno.: " + tmpAluno.getMatricula() + " -> " + tmpAluno.getNome());
            
            System.out.print(" :> Turmas: ");
            for (Turma tmpTurma : tmpAluno.getListaTurmas()) {
               System.out.print(tmpTurma.getTurma() + " ");
            }
            System.out.println();
            
         }
         
         System.out.println("-----------------------------------------------------------");
         
         ArrayList<Turma> listaTurmas = Turma.obtemListaDeTurmas();
         for(Turma tmpTurma : listaTurmas) {
            System.out.println("--> Turma: " + tmpTurma.getTurma());
            
            System.out.print(" :> Alunos: ");
            for (Aluno tmpAluno : Aluno.obtemListaAlunosPelaTurma(tmpTurma)) {
               System.out.print(tmpAluno.getMatricula() + " ");
            }
            System.out.println();
         }
         
         Connection c = Conexao.obtemConexao();
         c.setAutoCommit(false);
         
         Statement st = c.createStatement();
         st.execute("DELETE FROM disciplinas");
         
         Disciplina d = new Disciplina("TP2012","Tecnologia de Programacao em 2012");
         d.remove();
         
         c.commit();
         c.setAutoCommit(true);
         
         
      } catch (Exception ex) {
         System.out.println(ex.getMessage());
      }
   }
}
