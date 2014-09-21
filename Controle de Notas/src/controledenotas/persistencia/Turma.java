/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controledenotas.persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Leandro
 */
public class Turma {
   
   private String turma;
   private ArrayList<Aluno> listaAlunos;
   
   public String getTurma() { return this.turma; }
   public ArrayList<Aluno> obtemListaAlunos() throws SQLException { return Aluno.obtemListaAlunosPelaTurma(this); }
   
   public Turma(String turma) { this.turma = turma; }
   
   public static ArrayList<Turma> obtemListaDeTurmas() throws SQLException {
      
      Connection c = Conexao.obtemConexao();
      Statement st = c.createStatement();
      
      ResultSet rs = st.executeQuery("SELECT * FROM turmas");
      
      ArrayList<Turma> listaTurmas = new ArrayList<Turma>();
      
      while (rs.next()) {
         Turma tmpTurma = new Turma(rs.getString("turma"));
         listaTurmas.add(tmpTurma);
      }
      
      rs.close();
      st.close();
      
      return listaTurmas;
   }
   
   public static ArrayList<Turma> obtemListaDeTurmasDoAluno(Aluno aluno) throws SQLException {
      
      Connection c = Conexao.obtemConexao();
      Statement st = c.createStatement();
      
      ResultSet rs = st.executeQuery("SELECT turma "
                                     + "FROM Alunos INNER JOIN alunos_turma "
                                     + "ON alunos.matricula = alunos_turma.aluno " 
                                     + "WHERE alunos_turma.aluno = \'" + aluno.getMatricula() + "\'");
      
      // Processa todos os registros encontrados
      ArrayList<Turma> listaTurmas = new ArrayList<Turma>();
      while(rs.next()) {
         Turma tmpTurma = new Turma(rs.getString("turma"));
         listaTurmas.add(tmpTurma);
      }
      rs.close();
      st.close();
      
      return listaTurmas;  
   }   
   
}
