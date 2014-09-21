/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controledenotas.persistencia;

import controledenotas.Excecoes.RegistroNaoEncontradoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Leandro
 */
public class Aluno {
   
   private String matricula_o;
   private String matricula;
   private String nome;
   private boolean novo;

   public String getMatricula() {
      return matricula;
   }

   public void setMatricula(String matricula) {
      this.matricula = matricula;
   }

   public String getNome() {
      return nome;
   }

   public void setNome(String nome) {
      this.nome = nome;
   }
   
   public ArrayList<Turma> getListaTurmas() throws SQLException { 
      return Turma.obtemListaDeTurmasDoAluno(this);
   }
  
   /**
    * 
    * @param matricula
    * @param nome 
    */
   public Aluno(String matricula, String nome) {
      this(matricula,nome,true);
   }
   
   /**
    * 
    * @param matricula
    * @param nome
    * @param novo 
    */
   private Aluno(String matricula, String nome, boolean novo) {
      this.matricula_o = matricula;
      this.matricula = matricula;
      this.nome = nome;
      this.novo = novo;      
   }
   
   /**
    * 
    * @throws SQLException 
    */
   private void insere() throws SQLException {

      Connection c = Conexao.obtemConexao();
      Statement st = c.createStatement();
      
      int nRegs = st.executeUpdate("INSERT INTO Alunos VALUES(" +
                                   "\'" + this.matricula + "\'," +
                                   "\'" + this.nome + "\')");
      st.close();
      // Faz o original ser igual ao modificado
      this.matricula_o = this.matricula;
      this.novo = false;
   }
   
   /**
    * 
    * @throws SQLException 
    */
   private void altera() throws SQLException {
      
      Connection c = Conexao.obtemConexao();
      Statement st = c.createStatement();
      
      int nRegs = st.executeUpdate("UPDATE Alunos SET " +
                                   "matricula=\'" + this.matricula + "\'," +
                                   "nome=\'" + this.nome + "\' " +
                                   "WHERE matricula=\'" + this.matricula_o + "\'");
      st.close();
      // Faz o original ser igual ao modificado
      this.matricula_o = this.matricula;
      this.novo = false;
   }
   
   /**
    * 
    * @throws SQLException 
    */
   public void persiste() throws SQLException {
      if (this.novo) {
         insere();
      } else {
         altera();
      }
   }
   
   /**
    * 
    * @throws SQLException 
    */
   public void remove() throws SQLException {
      
      Connection c = Conexao.obtemConexao();
      Statement st = c.createStatement();
      
      int nRegs = st.executeUpdate("DELETE FROM Alunos WHERE matricula=\'" + this.matricula_o + "\'");
      st.close();
      // Faz o original ser igual ao modificado
      this.matricula_o = this.matricula;
      this.novo = true;
   }
   
   /**
    * 
    * @return
    * @throws SQLException 
    */
   public static ArrayList<Aluno> obtemListaAlunos() throws SQLException {
      
      Connection c = Conexao.obtemConexao();
      Statement st = c.createStatement();
      
      ResultSet rs = st.executeQuery("SELECT * FROM Alunos");
      
      // Processa todos os registros encontrados
      ArrayList<Aluno> listaAlunos = new ArrayList<Aluno>();
      while(rs.next()) {
         Aluno tmpAluno = new Aluno(rs.getString("matricula"), rs.getString("nome"), false);
         listaAlunos.add(tmpAluno);
      }
      rs.close();
      st.close();
      
      return listaAlunos;
   }
   
   /**
    * 
    * @param matricula
    * @return
    * @throws SQLException
    * @throws RegistroNaoEncontradoException 
    */
   public static Aluno obtemAlunoPeloNumeroDeMatricula(String matricula) throws SQLException, RegistroNaoEncontradoException {
      
      Connection c = Conexao.obtemConexao();
      Statement st = c.createStatement();
      
      ResultSet rs = st.executeQuery("SELECT * FROM Alunos WHERE matricula=\'" + matricula + "\'");
      
      // Verifica se o resultado está vazio
      if (!rs.next()) {
         throw new RegistroNaoEncontradoException();
      }
      
      // Processa o registro
      Aluno tmpAluno = new Aluno(rs.getString("matricula"), rs.getString("nome"), false);
      rs.close();
      st.close();
      
      return tmpAluno;
   }
   
   public static ArrayList<Aluno> obtemListaAlunosPelaTurma(Turma turma) throws SQLException {
      
      Connection c = Conexao.obtemConexao();
      Statement st = c.createStatement();
      
      ResultSet rs = st.executeQuery("SELECT matricula,nome "
                                     + "FROM Alunos LEFT JOIN alunos_turma "
                                     + "ON alunos.matricula = alunos_turma.aluno " 
                                     + "WHERE alunos_turma.turma = \'" + turma.getTurma()+ "\'");
      
      // Processa todos os registros encontrados
      ArrayList<Aluno> listaAlunos = new ArrayList<Aluno>();
      while(rs.next()) {
         Aluno tmpAluno = new Aluno(rs.getString("matricula"), rs.getString("nome"), false);
         listaAlunos.add(tmpAluno);
      }
      rs.close();
      st.close();
      
      return listaAlunos;
   }
   
   public void matriculaNaTurma(Turma turma) throws SQLException {
      
      Connection c = Conexao.obtemConexao();
      Statement st = c.createStatement();
      
      int nRegs = st.executeUpdate("INSERT INTO Alunos_turma VALUES (\'" 
                                   + this.matricula_o + "\',\'"
                                   + turma.getTurma() + "\')");
      
      st.close();      
   }
}
