package conexaoBanco;

import javax.servlet.http.HttpServlet;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.sql.Connection;

public class ConexaoBanco extends HttpServlet{
	
	protected Connection conexao = null;
	private String dbUrl = "jdbc:postgresql://localhost:5433/slac";
	private static ConexaoBanco instanciacon = null;


	// CONSTRUTOR DA CLASSE
	private ConexaoBanco() {
		try {
			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		}

		try {
			conexao = (Connection) DriverManager.getConnection(dbUrl, "postgres", "");
		} catch (Exception e) {
			System.out.println(e.getMessage()+"Exceção geral-ConexaoBancoServlet!");
		}
	}
	
	// SINGLETON PARA USAR NA CONEXAO COM O BANCO
		public static ConexaoBanco obterInstancia() {
			if (instanciacon == null) {
				instanciacon = new ConexaoBanco();
			}
			return instanciacon;
		}
		
}
