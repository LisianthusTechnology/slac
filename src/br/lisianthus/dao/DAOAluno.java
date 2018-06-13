package br.lisianthus.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.lisianthus.modelo.Aluno;
import br.lisianthus.modelo.Coordenador;
import br.lisianthus.modelo.Participacao;
import br.lisianthus.utils.Retorno;

public class DAOAluno {

	private static DAOAluno dao_a;
	private Connection con_a;
	
	
	private DAOAluno() {
		try {
			String driver_a = "org.postgresql.Driver";
			Class.forName(driver_a);
			this.con_a = DriverManager.getConnection("jdbc:postgresql://localhost:5433/slac", "postgres", "");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage() + "DAOAluno");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static DAOAluno getInstance() {
		if (dao_a == null) {
			dao_a = new DAOAluno();
		}
		return dao_a;
	}

	public String preparaAtributoParaBD(Object atributoValue) {
		String auxNome = "NULL";
		if (atributoValue != null) {
			auxNome = "'" + atributoValue + "'";
		}
		return auxNome;
	}
	

	public Retorno inserir_data_conclusao(Aluno aluno){
		Retorno ret = new Retorno(false, "erro");
		//Retorno okValidar = validar(aluno);
		if (aluno == null) {
			ret.setSucesso(false);
			ret.setMensagem("ERRO na inclusão da DATA do ALUNO realizada com sucesso");
			return ret;
		}
		
		String sql = "update aluno set data_conclusao_carga = "+preparaAtributoParaBD(aluno.getData_carga_total_part())
					+" where id_aluno= " + aluno.getId_aluno();
		System.out.println("SQL-Aluno:" + sql);
		

		int ok = 0;
		try {
			ok = executaAlteracao(sql);
		} catch (SQLException e) {
			// e.printStackTrace();
			String message = e.getMessage();
			ret.setSucesso(false);
			ret.setMensagem(message);
			
		}
		
		if (ok > 0) {
			ret.setSucesso(true);
			ret.setMensagem("inclusão da DATA do ALUNO realizada com sucesso");
		}

		System.out.println("Retorno:" + ret.getMensagem());

		return ret;
		
	}
	public Retorno inserir(Aluno aluno) {
		Retorno ret = new Retorno(false, "erro");
		boolean permissao_aluno = true;
		Retorno okValidar = validar(aluno);
		if (!okValidar.isSucesso()) {
			return okValidar;
		}

		String sql = "insert into aluno(cpf, nome, senha, email, matricula, ano_ingresso, permissao, coordenador_ac_id_admin)"
				+ " values(" + "" + preparaAtributoParaBD(aluno.getCpf()) + ","
				+ preparaAtributoParaBD(aluno.getNome_aluno()) + "," + preparaAtributoParaBD(aluno.getSenha()) + ","
				+ preparaAtributoParaBD(aluno.getEmail()) + "," + preparaAtributoParaBD(aluno.getMatricula()) + ","
				+ preparaAtributoParaBD(aluno.getAno_ingresso()) + "," + preparaAtributoParaBD(aluno.isPermissao())
				+ "," + preparaAtributoParaBD(aluno.getCoord_ac_id()) + ")";
		System.out.println("SQL-Aluno:" + sql);
		int ok = 0;
		try {
			ok = executaAlteracao(sql);
		} catch (SQLException e) {
			// e.printStackTrace();
			String message = e.getMessage();
			if (e.getMessage().contains("Aluno_pkey")) {
				message = "ERRO:01 - Já existe um Aluno com este id ";
			}
			ret.setSucesso(false);
			ret.setMensagem(message);
		}
		if (ok > 0) {
			ret.setSucesso(true);
			ret.setMensagem("inclusão do Aluno realizada com sucesso");
		}

		System.out.println("Retorno:" + ret.getMensagem());

		return ret;

	}

	private Retorno validar(Aluno aluno) {
		Retorno ret = new Retorno(true, "");

		if (aluno == null) {
			ret.setSucesso(false);
			ret.setMensagem("Aluno não foi definido, objeto inválido");
		} else if (aluno.getNome_aluno() == null || aluno.getNome_aluno().equals("")) {
			ret.setSucesso(false);
			ret.setMensagem("O campo Nome é de preenchimento obrigatório");
		} 

		return ret;
	}

	private int executaAlteracao(String sql) throws SQLException {
		Statement stmt = con_a.createStatement();
		// System.out.println("SQL 2:"+sql);
		int ok = stmt.executeUpdate(sql);
		return ok;
	}

	public boolean excluir(Aluno aluno) throws RuntimeException {
		if (aluno == null)
			return false;// o id nunca vai ser nulo

		String sql = "delete from aluno" + " where id_aluno='" + aluno.getId_aluno() + "' ";// TODO
																							// repetido
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

	
	public Retorno alterar(Aluno aluno) throws RuntimeException {
		Retorno testeret;
		
		testeret = validar(aluno);
			

		String sql = "update aluno set permissao = "+ preparaAtributoParaBD(aluno.isPermissao()) + " where id_aluno= " + aluno.getId_aluno()+ "";

		System.out.println("SQL_update:" + sql);
		
		int ok;
		
		try {
			ok = executaAlteracao(sql);
			if (ok > 0) {
				testeret.setSucesso(true);
				testeret.setMensagem("ALTERAÇÃO do Aluno realizada com sucesso");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			//System.out.println(e.getMessage());
			//throw new RuntimeException(e.getMessage());// tratar erros depois }
		}
		//System.out.println("Retorno:" + ret.getMensagem());


		return testeret;

	}

	/**
	 * 
	 * localiza uma lista de Alunos procurando pelo id(busca exata), ou pelo
	 * nome busca aproximada(com like no fim ex. joao%) O objeto Aluno deve ser
	 * preenchido o campo id ou nome, os demais são desconsiderados.
	 * 
	 * @param Aluno
	 * @return
	 * @throws SQLException
	 */
	public List<Aluno> localizar(Aluno aluno) throws RuntimeException {
		ArrayList<Aluno> list = new ArrayList<Aluno>();

		String sql = "select id_aluno, cpf, nome, senha, email, matricula, ano_ingresso, permissao, coordenador_ac_id_admin from aluno";

		String auxId = "";
		String auxNome = "";
		if (aluno != null && aluno.getId_aluno() != null) {
			auxId = "id_aluno=" + aluno.getId_aluno();
		}

		if (aluno != null && aluno.getCpf() != null) {
			auxNome = "cpf like '" + aluno.getCpf() + "%'";
		}

		if (!auxId.isEmpty()) {
			sql = sql + " where " + auxId;
		} else if (!auxNome.isEmpty()) {
			sql = sql + " where " + auxNome;
		}
		ResultSet result = null;
		System.out.println("SQL localizar aluno:" + sql);
		try {
			result = con_a.createStatement().executeQuery(sql);

			while (result.next()) {

				Integer aluno_id = result.getInt("id_aluno");
				Long cpf_a = result.getLong("cpf");
				String nome_a = result.getString("nome");
				String senha_a = result.getString("senha");
				String email_a = result.getString("email");
				Integer matricula_a = result.getInt("matricula");
				Integer ano_ingresso_a = result.getInt("ano_ingresso");
				boolean permissao_a = result.getBoolean("permissao");
				Integer coord_id_a = result.getInt("coordenador_ac_id_admin");

				Aluno a = new Aluno(aluno_id, matricula_a, ano_ingresso_a, coord_id_a, cpf_a, nome_a, senha_a, email_a,
						permissao_a);

				list.add(a);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}

		return list;
	}

	public Aluno obter(Integer id) {
		if (id == null)
			return null;
		String sql = "select id_aluno, cpf, nome, senha, email, matricula, ano_ingresso, permissao, coordenador_ac_id_admin from aluno where id_aluno=" + id + "";

		try {
			Statement stmt = con_a.createStatement();

			ResultSet resultSet = stmt.executeQuery(sql);
			if (resultSet.next()) {
				Aluno aluno = new Aluno();

				aluno.setId_aluno(resultSet.getInt("id_aluno"));
				aluno.setCpf(resultSet.getLong("cpf"));
				aluno.setNome_aluno(resultSet.getString("nome"));
				aluno.setSenha(resultSet.getString("senha"));
				aluno.setEmail(resultSet.getString("email"));
				aluno.setMatricula(resultSet.getInt("matricula"));
				aluno.setAno_ingresso(resultSet.getInt("ano_ingresso"));
				aluno.setPermissao(resultSet.getBoolean("permissao"));
				aluno.setCoord_ac_id(resultSet.getInt("coordenador_ac_id_admin"));
				
				return aluno;
			} else {
				return null;
			}

		} catch (SQLException e) { // TODO remover após conclusão
			e.printStackTrace();
			return null;
		}
	}
	
	public Aluno obter(Aluno loginaluno){
		String sql = "select id_aluno, cpf, nome, senha, email, matricula, "
				+ "ano_ingresso, permissao, coordenador_ac_id_admin from aluno where cpf = '" 
				+ loginaluno.getCpf()+ "' AND senha = '" + loginaluno.getSenha() + "'";
		
		try {
			Statement stmt = con_a.createStatement();

			ResultSet resultSet = stmt.executeQuery(sql);
			if (resultSet.next()) {
				Aluno aluno = new Aluno();

				aluno.setId_aluno(resultSet.getInt("id_aluno"));
				aluno.setCpf(resultSet.getLong("cpf"));
				aluno.setNome_aluno(resultSet.getString("nome"));
				aluno.setSenha(resultSet.getString("senha"));
				aluno.setEmail(resultSet.getString("email"));
				aluno.setMatricula(resultSet.getInt("matricula"));
				aluno.setAno_ingresso(resultSet.getInt("ano_ingresso"));
				aluno.setPermissao(resultSet.getBoolean("permissao"));
				aluno.setCoord_ac_id(resultSet.getInt("coordenador_ac_id_admin"));
				
				return aluno;
			} else {
				return null;
			}

		} catch (SQLException e) { // TODO remover após conclusão
			e.printStackTrace();
			return null;
			}
		}

	public List<Aluno> listaParaRelatorio(Coordenador coord){
		
		ArrayList<Aluno> list = new ArrayList<Aluno>();
		
		String sql = "select "
				+ "a.nome, a.matricula, a.ano_ingresso, a.data_conclusao_carga, c.nome_admin "
				+ "from aluno a inner join coordenador_ac c on a.coordenador_ac_id_admin = "+coord.getId_admin()+
				" where a.data_conclusao_carga is not null";
		
		try{
			Statement stmt = con_a.createStatement();
			ResultSet results = stmt.executeQuery(sql);
			
			
			while(results.next()){
			//	Aluno aluno = new Aluno();
			//	aluno.setNome_admin(results.getString("nome_admin"));
			//	aluno.setAno_ingresso(results.getInt("ano_ingresso"));
			//	aluno.setData_conlusao_carga(results.getDate("data_conclusao_carga"));
			//	aluno.setMatricula(results.getInt("matricula"));
			//	aluno.setNome(results.getString("nome"));
			//	System.out.println("SQL BUSCA:"+aluno.getData_conlusao_carga());
				Integer matricula = results.getInt("matricula");
					String nome = results.getString("nome");
					Integer ano_ingresso = results.getInt("ano_ingresso");
					Date data_conclusao_carga = results.getDate("data_conclusao_carga");
					System.out.println("Alunos relatorio:"+data_conclusao_carga);
					String nome_admin = results.getString("nome_admin");
				//System.out.println("SQL BUSCA:"+data_conclusao_carga);
					
				Aluno aluno = new Aluno(nome_admin,nome, matricula, ano_ingresso, data_conclusao_carga);
				
				list.add(aluno);
				
			}
		}catch(SQLException e){
			//System.out.println("teste"+ e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		
		return list;
	}

	public Aluno verfica_data_aluno(Integer id) {
		
		String sql = "select data_conclusao_carga from aluno where id_aluno = "+id;


		try {
			Aluno alun = new Aluno();
			Statement stmt = con_a.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);
			if (resultSet.next()) {
				
				alun.setData_conclusao_carga(resultSet.getDate("data_conclusao_carga"));
				return alun;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return null;
	}
}
