package br.lisianthus.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.lisianthus.modelo.Aluno;
import br.lisianthus.modelo.Coordenador;
import br.lisianthus.modelo.Participacao;
import br.lisianthus.utils.Mensagens;
import br.lisianthus.utils.Retorno;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

public class DAOAluno {

	private static DAOAluno dao_a;
	private Connection con_a;
	private Mensagens mesg = new Mensagens();
	
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
		Mensagens msg = new Mensagens();
		
		//Retorno okValidar = validar(aluno);
		if (aluno == null) {
			ret.setSucesso(false);
			ret.setMensagem(msg.ERRO4);
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
			ret.setMensagem("Data de Conclusão da Carga Horária "+msg.SUCESSO);
		}

		System.out.println("Retorno:" + ret.getMensagem());

		return ret;
		
	}
	public Retorno inserir(Aluno aluno) {
		Retorno ret = new Retorno(false, "erro");
		Mensagens msg = new Mensagens();
		
		Retorno okValidar = validar(aluno);
		if (!okValidar.isSucesso()) {
			return okValidar;
		}

		Retorno validaCPF = verificaCPF(aluno.getCpf());
		if(!validaCPF.isSucesso()){
			return validaCPF;
		}
		
		String sql = "insert into aluno(cpf, nome, senha, email, matricula, ano_ingresso, permissao)"
				+ " values(" + "" + preparaAtributoParaBD(aluno.getCpf()) + ","
				+ preparaAtributoParaBD(aluno.getNome_aluno()) + "," + preparaAtributoParaBD(aluno.getSenha()) + ","
				+ preparaAtributoParaBD(aluno.getEmail()) + "," + preparaAtributoParaBD(aluno.getMatricula()) + ","
				+ preparaAtributoParaBD(aluno.getAno_ingresso()) + "," + preparaAtributoParaBD(aluno.isPermissao())+ ")";
		System.out.println("SQL-Aluno:" + sql);
		
		int ok = 0;
		try {
			ok = executaAlteracao(sql);
		} catch (SQLException e) {
			// e.printStackTrace();
			String message = e.getMessage();
			if (e.getMessage().contains("Aluno_pkey")) {
				message = msg.ERRO1;
			}
			
			ret.setSucesso(false);
			ret.setMensagem(message);
		}
		if (ok > 0) {
			ret.setSucesso(true);
			ret.setMensagem("Aluno "+msg.SUCESSO);
		}

		System.out.println("Retorno:" + ret.getMensagem());

		return ret;

	}

	private Retorno validar(Aluno aluno) {
		Retorno ret = new Retorno(true, "");
		Mensagens msg = new Mensagens();
		
		if (aluno == null) {
			ret.setSucesso(false);
			ret.setMensagem("Aluno "+msg.ERRO2);
		} else if (aluno.getNome_aluno() == null || aluno.getNome_aluno().equals("")) {
			ret.setSucesso(false);
			ret.setMensagem(msg.ERRO5);
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
		Mensagens msg = new Mensagens();
		
		testeret = validar(aluno);
			

		String sql = "update aluno set permissao = "+ preparaAtributoParaBD(aluno.isPermissao()) + " where id_aluno= " + aluno.getId_aluno()+ "";

		System.out.println("SQL_update:" + sql);
		
		int ok;
		
		try {
			ok = executaAlteracao(sql);
			if (ok > 0) {
				testeret.setSucesso(true);
				testeret.setMensagem(msg.ALTERASTATUS);
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

		String sql = "select id_aluno, cpf, nome, senha, email, matricula, ano_ingresso, permissao from aluno";

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
				Long matricula_a = result.getLong("matricula");
				Integer ano_ingresso_a = result.getInt("ano_ingresso");
				boolean permissao_a = result.getBoolean("permissao");

				Aluno a = new Aluno(aluno_id, matricula_a, ano_ingresso_a, cpf_a, nome_a, senha_a, email_a,
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
				aluno.setMatricula(resultSet.getLong("matricula"));
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
				+ "ano_ingresso, permissao from aluno where cpf = '" 
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
				aluno.setMatricula(resultSet.getLong("matricula"));
				aluno.setAno_ingresso(resultSet.getInt("ano_ingresso"));
				aluno.setPermissao(resultSet.getBoolean("permissao"));
				
				return aluno;
			} else {
				return null;
			}

		} catch (SQLException e) { // TODO remover após conclusão
			e.printStackTrace();
			return null;
			}
		}
	
	public Retorno verificaCPF(Long cpf) throws RuntimeException{
		Retorno ret = new Retorno();
		System.out.println(cpf);
		String sql = "select count (*) from aluno where cpf = '" + cpf + "'";
		Statement stmt;
		try {
			stmt = con_a.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);
			resultSet.next();
			int cpfExistente = resultSet.getInt(1);
			System.out.println("CPF EXISTENTE:" + cpfExistente);
			
			if(cpfExistente > 0){
				ret.setMensagem(mesg.ERRO9);
				ret.setSucesso(false);
				return ret;
			}else{
				ret.setSucesso(true);
				return ret;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

public List<Aluno> listaParaRelatorio(Date data_inicio, Date data_fim){
		
		ArrayList<Aluno> list = new ArrayList<Aluno>();
		SimpleDateFormat formatodata = new SimpleDateFormat("yyyy-mm-dd");
		
		String sql = "select * from aluno where data_conclusao_carga BETWEEN '"+formatodata.format(data_inicio)+"' AND '"+formatodata.format(data_fim)+"'";
		
		System.out.println("Minha SQL:"+sql);
		try{
			Statement stmt = con_a.createStatement();
			ResultSet results = stmt.executeQuery(sql);

			//Date datainicio = data_inicio;
			//Date datafim = data_fim;
			//Aluno aluno = new Aluno();
			//aluno.setData_inicio(datainicio);
			//aluno.setData_fim(datafim);
			
			while(results.next()){
	
					Long matricula = results.getLong("matricula");
					String nome = results.getString("nome");
					Integer ano_ingresso = results.getInt("ano_ingresso");
					Date data_conclusao_carga = results.getDate("data_conclusao_carga");
					System.out.println("Alunos relatorio:"+data_conclusao_carga);
		/*
					aluno.setNome(nome);
					aluno.setMatricula(matricula);
					aluno.setAno_ingresso(ano_ingresso);
					aluno.setData_carga_total_part(data_conclusao_carga);*/
				Aluno aluno = new Aluno(nome, matricula, ano_ingresso, data_conclusao_carga);
				
					
					list.add(aluno);
				//	aluno.setData_inicio("");
				//	aluno.setData_fim("");
				
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
