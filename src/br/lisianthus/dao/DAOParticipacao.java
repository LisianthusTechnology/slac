package br.lisianthus.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.lisianthus.modelo.Aluno;
import br.lisianthus.modelo.Participacao;
import br.lisianthus.utils.Mensagens;
import br.lisianthus.utils.Retorno;

//teste git
public class DAOParticipacao {
	private static DAOParticipacao dao;
	private Connection con;

	private DAOParticipacao() {
		try {
			String driver = "org.postgresql.Driver";
			Class.forName(driver);
			this.con = DriverManager.getConnection("jdbc:postgresql://localhost:5433/slac", "postgres", "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage() + "DAOPArticipacao");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static DAOParticipacao getInstance() {
		if (dao == null) {
			dao = new DAOParticipacao();
		}
		return dao;
	}

	public String preparaAtributoParaBD(Object atributoValue) {
		String auxNome = "NULL";
		if (atributoValue != null) {
			auxNome = "'" + atributoValue + "'";
		}
		return auxNome;
	}

	public Participacao verificar_carga_horaria() {
		String sql = "select aluno_id_aluno, SUM(ch_validada_part) as ch_validada_part from participacao where status = 'VALIDADO' group by aluno_id_aluno";


		try {
			Participacao participacao = new Participacao();
			Statement stmt = con.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);
			if (resultSet.next()) {
				
				participacao.setAluno_id_aluno(resultSet.getInt("aluno_id_aluno"));
				participacao.setCh_validada_part(resultSet.getInt("ch_validada_part"));
				System.out.println("DAOParticipacao: "+participacao.getCh_validada_part());
				return participacao;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return null;

	}

	public Retorno inserir(Participacao participacao) {
		Retorno ret = new Retorno(false, "erro");
		Mensagens msg = new Mensagens();
		Retorno okValidar = validar(participacao);
		if (!okValidar.isSucesso()) {
			return okValidar;
		}
		// id_participacao,
		String sql = "insert into participacao(atividade_complementar_id_atividade, aluno_id_aluno, "
				+ " certificado_part, coordenador_ac_id_admin, status, data_validacao_ac, nome_ac_part, "
				+ "data_inicio_ac_part, ch_cadastrada_part, ch_validada_part, local_ac_part, tipo_ac_part)" + " values("
				+ "" + preparaAtributoParaBD(participacao.getAtividade_complementar_id_atividade()) + ", "
				+ preparaAtributoParaBD(participacao.getAluno_id_aluno()) + ","
				// + participacao.getId_participacao() + ","
				+ preparaAtributoParaBD(participacao.getCertificado_part()) + ","
				+ preparaAtributoParaBD(participacao.getCoordenador_ac_id_admin()) + ","
				+ preparaAtributoParaBD(participacao.getStatus()) + ","
				+ preparaAtributoParaBD(participacao.getData_validaca_ac()) + ","
				+ preparaAtributoParaBD(participacao.getNome_ac_part()) + ","
				+ preparaAtributoParaBD(participacao.getData_inicio_ac_part()) + ","
				+ preparaAtributoParaBD(participacao.getCh_cadastrada_part()) + ","
				+ preparaAtributoParaBD(participacao.getCh_validada_part()) + ","
				+ preparaAtributoParaBD(participacao.getLocal_ac_part()) + ","
				+ preparaAtributoParaBD(participacao.getTipo_ac_part()) + ")";
		System.out.println("SQL:" + sql);
		int ok = 0;
		try {
			ok = executaSQL(sql);
		} catch (SQLException e) {
			// e.printStackTrace();
			String message = e.getMessage();
			if (e.getMessage().contains("participacao_pkey")) {
				message = msg.ERRO1;
			}
			ret.setSucesso(false);
			ret.setMensagem(message);
		}
		if (ok > 0) {
			ret.setSucesso(true);
			ret.setMensagem("Participa��o "+msg.SUCESSO);
		}

		System.out.println("Retorno DAO:" + ret.getMensagem());

		return ret;

	}

	private Retorno validar(Participacao participacao) {
		Retorno ret = new Retorno(true, "");
		Mensagens msg = new Mensagens();
		if (participacao == null) {
			ret.setSucesso(false);
			ret.setMensagem("Participa��o "+msg.ERRO2);
		} else if (participacao.getNome_ac_part() == null || participacao.getNome_ac_part().equals("")) {
			ret.setSucesso(false);
			ret.setMensagem(msg.ERRO3);
		}
		return ret;
	}

	private int executaSQL(String sql) throws SQLException {
		Statement stmt = con.createStatement();
		// System.out.println("SQL 2:"+sql);
		int ok = stmt.executeUpdate(sql);
		return ok;
	}

	public boolean excluir(Participacao participacao) throws RuntimeException {
		if (participacao == null)
			return false;// o id nunca vai ser nulo

		String sql = "delete from participacao" + " where id='" + participacao.getId_participacao() + "' ";// TODO
																											// repetido
		System.out.println("SQL_delete:" + sql);

		int ok;
		try {
			ok = executaSQL(sql);
			if (ok > 0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage());
		}

		return false;
	}

	public Retorno alterar(Participacao participacao) throws RuntimeException {
		Retorno retorno_part;
		Mensagens msg = new Mensagens();
		retorno_part = validar(participacao);

		String sql = "update participacao set status= " + preparaAtributoParaBD(participacao.getStatus())
				+ " where id_participacao = '" + participacao.getId_participacao() + "'";

		System.out.println("SQL_update:" + sql);

		int ok;
		try {
			ok = executaSQL(sql);
			if (ok > 0) {
				retorno_part.setSucesso(true);
				retorno_part.setMensagem(msg.ALTERASTATUS);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage());// tratar erros depois
		}

		return retorno_part;
	}

	/**
	 * 
	 * localiza uma lista de participacaos procurando pelo id(busca exata), ou
	 * pelo nome busca aproximada(com like no fim ex. joao%) O objeto
	 * participacao deve ser preenchido o campo id ou nome, os demais s�o
	 * desconsiderados.
	 * 
	 * @param participacao
	 * @return
	 * @throws SQLException
	 */
	public List<Participacao> localizar(Participacao participacao) throws RuntimeException {
		ArrayList<Participacao> list = new ArrayList<Participacao>();

		String sql = "select atividade_complementar_id_atividade, aluno_id_aluno, id_participacao, certificado_part, coordenador_ac_id_admin,"
				+ "status, data_validacao_ac, nome_ac_part, data_inicio_ac_part, ch_cadastrada_part, ch_validada_part, local_ac_part, tipo_ac_part "
				+ "from participacao ";

		String auxId = "";
		String auxNome = "";
		if (participacao != null && participacao.getId_participacao() != null) {
			auxId = "id_participacao=" + participacao.getId_participacao();
		}

		if (participacao != null && participacao.getNome_ac_part() != null) {
			auxNome = "nome_ac_part like '" + participacao.getNome_ac_part() + "%'";
		}

		if (!auxId.isEmpty()) {
			sql = sql + " where " + auxId;
		} else if (!auxNome.isEmpty()) {
			sql = sql + " where " + auxNome;
		}
		ResultSet result = null;
		System.out.println("SQL localizar:" + sql);
		try {
			result = con.createStatement().executeQuery(sql);

			while (result.next()) {

				Integer atividade_comp_id = result.getInt("atividade_complementar_id_atividade");
				Integer aluno_id = result.getInt("aluno_id_aluno");
				Integer participacao_id = result.getInt("id_participacao");
				String certificado_part = result.getString("certificado_part");
				Integer coord_id = result.getInt("coordenador_ac_id_admin");
				String status_part = result.getString("status");
				Date data_val = result.getDate("data_validacao_ac");
				String nome_ac = result.getString("nome_ac_part");
				Date data_inicio = result.getDate("data_inicio_ac_part");
				Integer ch_cadastrada = result.getInt("ch_cadastrada_part");
				Integer ch_validada = result.getInt("ch_validada_part");
				String local_ac = result.getString("local_ac_part");
				String tipo_ac = result.getString("tipo_ac_part");

				Participacao a = new Participacao(atividade_comp_id, aluno_id, participacao_id, certificado_part,
						coord_id, status_part, data_val, nome_ac, data_inicio, ch_cadastrada, ch_validada, local_ac,
						tipo_ac);

				list.add(a);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}

		return list;
	}

	public List<Participacao> localizarConsulta(Participacao participacao, Aluno aluno) throws RuntimeException {

		ArrayList<Participacao> list = new ArrayList<Participacao>();

		String sql = "select atividade_complementar_id_atividade, aluno_id_aluno, id_participacao, "
				+ "status, nome_ac_part, ch_cadastrada_part, ch_validada_part " + "from participacao where aluno_id_aluno= '"+aluno.getId_aluno()+"'";

		String auxId = "";
		String auxNome = "";
		if (participacao != null && participacao.getId_participacao() != null) {
			auxId = "id_participacao=" + participacao.getId_participacao();
		}

		if (participacao != null && participacao.getNome_ac_part() != null) {
			auxNome = "nome_ac_part like '" + participacao.getNome_ac_part() + "%'";
		}

		if (!auxId.isEmpty()) {
			sql = sql + " where " + auxId;
		} else if (!auxNome.isEmpty()) {
			sql = sql + " where " + auxNome;
		}
		ResultSet result = null;
		System.out.println("SQL localizar:" + sql);
		try {
			result = con.createStatement().executeQuery(sql);

			while (result.next()) {

				Integer atividade_comp_id = result.getInt("atividade_complementar_id_atividade");
				Integer aluno_id = result.getInt("aluno_id_aluno");
				Integer participacao_id = result.getInt("id_participacao");
				String status_part = result.getString("status");
				String nome_ac = result.getString("nome_ac_part");
				Integer ch_cadastrada = result.getInt("ch_cadastrada_part");
				Integer ch_validada = result.getInt("ch_validada_part");

				Participacao a = new Participacao(atividade_comp_id, aluno_id, participacao_id, status_part, nome_ac,
						ch_cadastrada, ch_validada);

				list.add(a);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}

		return list;

	}

	public List<Participacao> localizarpartConsulta(Participacao participacao) throws RuntimeException {

		ArrayList<Participacao> list = new ArrayList<Participacao>();

		String sql = "select atividade_complementar_id_atividade, aluno_id_aluno, id_participacao, "
				+ "status, nome_ac_part, ch_cadastrada_part, ch_validada_part from participacao";
		String auxId = "";
		String auxNome = "";
		if (participacao != null && participacao.getId_participacao() != null) {
			auxId = "id_participacao=" + participacao.getId_participacao();
		}

		if (participacao != null && participacao.getNome_ac_part() != null) {
			auxNome = "nome_ac_part like '" + participacao.getNome_ac_part() + "%'";
		}

		if (!auxId.isEmpty()) {
			sql = sql + " where " + auxId;
		} else if (!auxNome.isEmpty()) {
			sql = sql + " where " + auxNome;
		}
		ResultSet result = null;
		System.out.println("SQL localizar:" + sql);
		try {
			result = con.createStatement().executeQuery(sql);

			while (result.next()) {

				Integer atividade_comp_id = result.getInt("atividade_complementar_id_atividade");
				Integer aluno_id = result.getInt("aluno_id_aluno");
				Integer participacao_id = result.getInt("id_participacao");
				String status_part = result.getString("status");
				String nome_ac = result.getString("nome_ac_part");
				Integer ch_cadastrada = result.getInt("ch_cadastrada_part");
				Integer ch_validada = result.getInt("ch_validada_part");

				Participacao a = new Participacao(atividade_comp_id, aluno_id, participacao_id, status_part, nome_ac,
						ch_cadastrada, ch_validada);

				list.add(a);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}

		return list;
	}
	
	public Participacao obter(Integer id) {
		if (id == null)
			return null;
		String sql = "select atividade_complementar_id_atividade, "
				+ "aluno_id_aluno, id_participacao, certificado_part, "
				+ "coordenador_ac_id_admin, status, data_validacao_ac, nome_ac_part, "
				+ "data_inicio_ac_part, ch_cadastrada_part, ch_validada_part, local_ac_part, tipo_ac_part "
				+ "from participacao where id_participacao=" + id + "";

		try {
			Statement stmt = con.createStatement();

			ResultSet resultSet = stmt.executeQuery(sql);
			if (resultSet.next()) {
				Participacao participacao = new Participacao();

				participacao.setAtividade_complementar_id_atividade(
						resultSet.getInt("atividade_complementar_id_atividade"));
				participacao.setAluno_id_aluno(resultSet.getInt("aluno_id_aluno"));
				participacao.setId_participacaoo(resultSet.getInt("id_participacao"));
				// participacao.setCertificado_part(resultSet.getString("certificado_part"));
				participacao.setCoordenador_ac_id_admin(resultSet.getInt("coordenador_ac_id_admin"));
				participacao.setStatus(resultSet.getString("status"));
				participacao.setData_validaca_ac(resultSet.getDate("data_validacao_ac"));
				participacao.setNome_ac_part(resultSet.getString("nome_ac_part"));
				participacao.setData_inicio_ac_part(resultSet.getDate("data_inicio_ac_part"));
				participacao.setCh_cadastrada_part(resultSet.getInt("ch_cadastrada_part"));
				participacao.setCh_validada_part(resultSet.getInt("ch_validada_part"));
				participacao.setLocal_ac_part(resultSet.getString("local_ac_part"));
				participacao.setTipo_ac_part(resultSet.getString("tipo_ac_part"));

				return participacao;
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO remover ap�s conclus�o
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

	// pegar o id da modalidade e passar para o daoAC para que esse retorne
	// todas as acs da modalidade
	// para cada id de ac retornado preciso verificar quantos part existem e
	// somar a ch_validade de todas elas
	// esse m�todo vai receber uma lista de id que vai ser percorrida e para
	// cada id eu terei que rodar a sql acima
}
