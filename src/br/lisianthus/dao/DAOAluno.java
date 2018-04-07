package br.lisianthus.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.lisianthus.modelo.Aluno;
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
	

	public Retorno inserir_mod(Aluno aluno) {
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

	private Long preparaAtributoLong(ResultSet rs, String atributo) throws SQLException {
		Long numeroLong = null;
		if (rs.getObject(atributo) != null) {
			numeroLong = rs.getLong(atributo);
		}
		return numeroLong;
	}
}
