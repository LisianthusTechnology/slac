package br.lisianthus.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.lisianthus.modelo.Modalidade;
import br.lisianthus.utils.Retorno;

public class DAOModalidade {
	
	private static DAOModalidade dao_m;
	private Connection con_m;
	
	private DAOModalidade(){
		
		
		try{
			String driver_m = "org.postgresql.Driver";
			Class.forName(driver_m);
			this.con_m = DriverManager.getConnection("jdbc:postgresql://localhost:5433/slac", "postgres", "");
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage()+"DAOModalidade");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static DAOModalidade getInstance(){
		if(dao_m == null){
			dao_m = new DAOModalidade();
		}
		return dao_m;
	}
	
	public String preparaAtributoParaBD(Object atributoValue){
		String auxNome = "NULL";
		if(atributoValue!=null){
			auxNome="'"+atributoValue+"'";
		}
		return auxNome;
	}
	
	public Retorno inserir_mod(Modalidade modalidade) {
		Retorno ret = new Retorno(false,"erro");
		
		Retorno okValidar = validar(modalidade);
		if(!okValidar.isSucesso())
		{
			return okValidar;
		}
		
		String sql = "insert into modalidade(id_mod, nome_mod)" + " values(" + "" 
		+ modalidade.getId_mod() + ","
		+ preparaAtributoParaBD(modalidade.getNome_mod()) + ")";
		System.out.println("SQL-Modalidade:" + sql);
		int ok = 0;
		try {
			ok = executaAlteracao(sql);
		} catch (SQLException e) {
			//e.printStackTrace();
			String message=e.getMessage();
			if(e.getMessage().contains("modalidade_pkey")){
				message = "ERRO:01 - Já existe um Modalidade com este id ";
			}
			ret.setSucesso(false);
			ret.setMensagem(message);
		}
		if (ok > 0) {
			ret.setSucesso(true);
			ret.setMensagem("inclusão do Modalidade realizada com sucesso");
		}
		
		System.out.println("Retorno:"+ ret.getMensagem());

		return ret;

	}

	private Retorno validar(Modalidade modalidade) {
		Retorno ret = new Retorno(true,"");
		
		if(modalidade== null){
			ret.setSucesso(false);
			ret.setMensagem("Modalidade não foi definido, objeto inválido");
		}else if(modalidade.getNome_mod() == null || 
				modalidade.getNome_mod().equals("")){
			ret.setSucesso(false);
			ret.setMensagem("O campo Nome é de preenchimento obrigatório");
		}else if(modalidade.getId_mod()== null || modalidade.getId_mod().intValue()<=0){
			ret.setSucesso(false);
			ret.setMensagem("O campo Id é de preenchimento obrigatório e deve ser maior que 0!");
		}
		
		return ret;
	}

	private int executaAlteracao(String sql) throws SQLException {
		Statement stmt = con_m.createStatement();
		//System.out.println("SQL 2:"+sql);
		int ok = stmt.executeUpdate(sql);
		return ok;
	}
	


	public boolean excluir(Modalidade modalidade) throws RuntimeException {
		if (modalidade == null) return false;// o id nunca vai ser nulo

		String sql = "delete from modalidade"
				+ " where id='" + modalidade.getId_mod() + "' ";//TODO repetido
		System.out.println("SQL_delete:" + sql);

		int ok;
		try {
			ok = executaAlteracao(sql);
			if (ok > 0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage());
		}
		
		return false;
	}

	public boolean alterar(Modalidade modalidade) throws RuntimeException {
		if (modalidade == null)
			return false;

		
		String sql = "update modalidade set nome_ac_part= '" + preparaAtributoParaBD(modalidade.getNome_mod()) + "' "
				+ " where id_Modalidade= '"+ modalidade.getId_mod() + "'";
		
		System.out.println("SQL_update:" + sql);

		int ok;
		try {
			ok = executaAlteracao(sql);
			if (ok > 0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage());//tratar erros depois
		}
		
		return false;
	}

	/**
	 * 
	 * localiza uma lista de Modalidades procurando pelo id(busca exata), ou pelo
	 * nome busca aproximada(com like no fim ex. joao%) O objeto Modalidade deve ser
	 * preenchido o campo id ou nome, os demais são desconsiderados.
	 * 
	 * @param Modalidade
	 * @return
	 * @throws SQLException 
	 */
	public List<Modalidade> localizar(Modalidade modalidade) throws RuntimeException {
		ArrayList<Modalidade> list = new ArrayList<Modalidade>();

		String sql = "select id_mod,nome_mod from modalidade";

		String auxId = "";
		String auxNome = "";
		if (modalidade != null && modalidade.getId_mod() != null) {
			auxId = "id_mod=" + modalidade.getId_mod();
		}

		if (modalidade != null && modalidade.getNome_mod()!= null) {
			auxNome = "nome_mod like '" + modalidade.getNome_mod() + "%'";
		}

		if (!auxId.isEmpty()) {
			sql = sql + " where " + auxId;
		} else if (!auxNome.isEmpty()) {
			sql = sql + " where " + auxNome;
		}
		ResultSet result = null;
		System.out.println("SQL localizar:"+sql);
		try {
			result = con_m.createStatement().executeQuery(sql);
			
			while(result.next()){
				 
			
				Integer modalidade_id = result.getInt("id_mod");
				String nome_mod = result.getString("nome_mod");
				
				Modalidade a = new Modalidade(modalidade_id, nome_mod);
				
				list.add(a);
			}	
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		
		return list;
	}

	public Modalidade obter(Integer id) {
		if(id == null) return null;
		String sql = "select id_mod, nome_ac_part from modalidade where id_mod=" + id + "";
	
		try {
			Statement stmt = con_m.createStatement();

			ResultSet resultSet = stmt.executeQuery(sql);
			if (resultSet.next()) {
				Modalidade modalidade = new Modalidade();
				
				modalidade.setId_mod(resultSet.getInt("id_mod"));
				modalidade.setNome_mod(resultSet.getString("nome_ac_part"));
				
				return modalidade;
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO remover após conclusão
			e.printStackTrace();
			return null;
		}
	}
	
	public Modalidade obterModalidade(Integer id) {
		if(id == null) return null;
		String sql = "select id_mod, nome_mod from modalidade where id_mod=" + id + "";
	
		try {
			Statement stmt = con_m.createStatement();

			ResultSet resultSet = stmt.executeQuery(sql);
			if (resultSet.next()) {
				Modalidade modalidade = new Modalidade();
				
				modalidade.setId_mod(resultSet.getInt("id_mod"));
				modalidade.setNome_mod(resultSet.getString("nome_mod"));
				
				return modalidade;
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO remover após conclusão
			e.printStackTrace();
			return null;
		}
	}
	
	
	private Long preparaAtributoLong(ResultSet rs, String atributo) throws SQLException{
		Long numeroLong = null;
		if(rs.getObject(atributo) != null){
			numeroLong = rs.getLong(atributo);	
		}
		return numeroLong;
	}
}
