/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controledenotas.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Leandro
 */
public class Conexao {
   
   private static Connection conexao;
   
   private static String URL_CONEXAO = "jdbc:mysql://localhost:3306/ControleNotas";
   private static String USUARIO = "root";
   private static String SENHA = "";

   /**
    * Obtém a conexão ativa.
    * @return conexao.
    */
   public static Connection obtemConexao() throws SQLException {
      
      // Se não há uma conexão, então cria uma
      if (conexao == null) {
         conexao = DriverManager.getConnection(URL_CONEXAO,USUARIO,SENHA);
      }
      // Retorna a conexão existente
      return conexao;      
   }
   
}
