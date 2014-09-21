/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controledenotas.persistencia;

import controledenotas.Excecoes.DadosIncompletosException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Leandro
 */
public class Disciplina {
   
   private String codigo_o;
   private String codigo;
   private String descricao;

   public String getCodigo() {
      return codigo;
   }

   public void setCodigo(String codigo) {
      this.codigo = codigo;
   }

   public String getDescricao() {
      return descricao;
   }

   public void setDescricao(String descricao) {
      this.descricao = descricao;
      this.onlyPK = false;
   }
   private boolean novo;
   private boolean onlyPK;
   
   public Disciplina(String codigo) {
      this(codigo,null,false,true);
   }
   
   public Disciplina(String codigo, String descricao) {
      this(codigo,descricao,true,false);
   }
   
   private Disciplina(String codigo, String descricao, boolean novo, boolean onlyPK) {
      this.codigo = codigo;
      this.codigo_o = codigo;
      this.descricao = descricao;
      this.novo = novo;
      this.onlyPK = onlyPK;
   }
   
   private void insere() throws SQLException, DadosIncompletosException {
      
      // Se ainda não tiver descrição, então não persiste
      if (this.onlyPK) throw new DadosIncompletosException();
      
      Connection c = Conexao.obtemConexao();
      
      Statement st = c.createStatement();
      
      int nRegs = st.executeUpdate("INSERT INTO disciplinas VALUES (" +
                                   "\'" + this.codigo + "\'," +
                                   "\'" + this.descricao + "\')");
      st.close();
      // Faz o original ser igual ao modificado
      this.codigo_o = this.codigo;
      this.novo = false;
      
   }
   
   private void altera() throws SQLException, DadosIncompletosException {
      
      // Se ainda não tiver descrição, então não persiste
      if (this.onlyPK) throw new DadosIncompletosException();
      
      Connection c = Conexao.obtemConexao();
      Statement st = c.createStatement();
      
      int nRegs = st.executeUpdate("UPDATE disciplinas SET " +
                                   "codigo=\'" + this.codigo + "\'," +
                                   "descricao=\'" + this.descricao + "\' " +
                                   "WHERE codigo=\'" + this.codigo_o + "\'");
      st.close();
      // Faz o original ser igual ao modificado
      this.codigo_o = this.codigo;
      this.novo = false;
      
   }   
   
   public void persiste() throws SQLException, DadosIncompletosException {
      if (this.novo) {
         insere();
      } else {
         altera();
      }
   }   
   
   public void remove() throws SQLException {
      
      Connection c = Conexao.obtemConexao();
      Statement st = c.createStatement();
      
      st.execute("DELETE FROM disciplinas WHERE codigo=\'" + this.codigo_o + "\'");
      st.close();
      // Faz o original ser igual ao modificado
      this.codigo_o = this.codigo;
      this.novo = true;
   }   
   
}
