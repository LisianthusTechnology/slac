package br.lisianthus.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.lisianthus.modelo.AtividadeComplementar;
import br.lisianthus.modelo.Coordenador;
import br.lisianthus.modelo.Modalidade;
import br.lisianthus.utils.Mensagens;
import br.lisianthus.utils.Retorno;

public class DAOCoordenador {

	private static DAOCoordenador dao_c;
	private Connection con_c;
	private Mensagens msg = new Mensagens();
	private DAOCoordenador(){
		
		
		try{
			String driver_m = "org.postgresql.Driver";
			Class.forName(driver_m);
			this.con_c = DriverManager.getConnection("jdbc:postgresql://localhost:5433/slac", "postgres", "");
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage()+"DAOCoordenador");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static DAOCoordenador getInstance(){
		if(dao_c == null){
			dao_c = new DAOCoordenador();
		}
		return dao_c;
	}
	
	
	
	private int executaAlteracao(String sql) throws SQLException {
		Statement stmt = con_c.createStatement();
		//System.out.println("SQL 2:"+sql);
		int ok = stmt.executeUpdate(sql);
		return ok;
	}
	
	
	
	public String preparaAtributoParaBD(Object atributoValue){
		String auxNome = "NULL";
		if(atributoValue!=null){
			auxNome="'"+atributoValue+"'";
		}
		return auxNome;
	}
	
	private Retorno validar(Coordenador coord) {
		Retorno ret = new Retorno(true,"");
		
		if(coord == null){
			ret.setSucesso(false);
			ret.setMensagem("Coordenador "+msg.ERRO2);
		}else if(coord.getNome() == null || 
				coord.getNome().equals("")){
			ret.setSucesso(false);
			ret.setMensagem(msg.ERRO5);
		}else if(coord.getId()== null || coord.getId().intValue()<=0){
			ret.setSucesso(false);
			ret.setMensagem(msg.ERRO6);
		}
		
		return ret;
	}

	public Retorno inserir(Coordenador coord) {
		Retorno ret = new Retorno(false,"erro");
		
		Retorno okValidar = validar(coord);
		if(!okValidar.isSucesso())
		{
			return okValidar;
		}
		
		String sql = "insert into coordenador_ac(nome_admin, login, senha)" + " values(" + "" 
		+ preparaAtributoParaBD(coord.getNome()) + ","
		+ preparaAtributoParaBD(coord.getLogin()) + "," 
		+ preparaAtributoParaBD(coord.getSenha()) + ")";
		System.out.println("SQL-Coordenador:" + sql);
		int ok = 0;
		try {
			ok = executaAlteracao(sql);
		} catch (SQLException e) {
			//e.printStackTrace();
			String message=e.getMessage();
			if(e.getMessage().contains("coordenador_pkey")){
				message = msg.ERRO1;
			}
			ret.setSucesso(false);
			ret.setMensagem(message);
		}
		if (ok > 0) {
			ret.setSucesso(true);
			ret.setMensagem("Coordenador "+msg.SUCESSO);
		}
		
		System.out.println("Retorno:"+ ret.getMensagem());

		return ret;

	}
	
	public Coordenador obter(Coordenador coord) {
		String sql = "select id_admin, nome_admin, login, senha from coordenador_ac where login = '" + 
				coord.getLogin() + "' AND senha = '" + coord.getSenha() + "'";
	
		try {
			Statement stmt = con_c.createStatement();

			ResultSet resultSet = stmt.executeQuery(sql);
			if (resultSet.next()) {
				Coordenador coordenador = new Coordenador();
				
				coordenador.setId(resultSet.getInt("id_admin"));
				coordenador.setNome(resultSet.getString("nome_admin"));
				coordenador.setLogin(resultSet.getString("login"));
				coordenador.setSenha(resultSet.getString("senha"));
				
				return coordenador;
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO remover após conclusão
			e.printStackTrace();
			return null;
		}
	}

}
