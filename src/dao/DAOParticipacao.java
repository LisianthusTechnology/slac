package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modelo.Participacao;
import utils.Retorno;


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
			System.out.println(e.getMessage()+"DAOPArticipacao");
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

	public String preparaAtributoParaBD(Object atributoValue){
		String auxNome = "NULL";
		if(atributoValue!=null){
			auxNome="'"+atributoValue+"'";
		}
		return auxNome;
	}
	
	
	public Retorno inserir(Participacao participacao) {
		Retorno ret = new Retorno(false,"erro");
		
		Retorno okValidar = validar(participacao);
		if(!okValidar.isSucesso())
		{
			return okValidar;
		}
		
		String sql = "insert into participacao(atividade_complementar_id_atividade, aluno_id_aluno, "
				+ "id_participacao, certificado_part, coordenador_ac_id_admin, status, data_validacao_ac, nome_ac_part, "
				+ "data_inicio_ac_part, ch_cadastrada_part, ch_validada_part, local_ac_part, tipo_ac_part)" + " values(" + "" 
		+ preparaAtributoParaBD(participacao.getAtividade_complementar_id_atividade()) + "" +", " 
		+ preparaAtributoParaBD(participacao.getAluno_id_aluno()) + ","
		+ participacao.getId_participacao() + ","
		+ preparaAtributoParaBD(participacao.getCertificado_part()) + ","
		+ preparaAtributoParaBD(participacao.getCoordenador_ac_id_admin()) + ","
		+ preparaAtributoParaBD(participacao.getStatus()) + ","
		+ preparaAtributoParaBD(participacao.getData_validaca_ac()) + ","
		+ preparaAtributoParaBD(participacao.getNome_ac_part()) + ","
		+ preparaAtributoParaBD(participacao.getData_inicio_ac_part()) + ","
		+ preparaAtributoParaBD(participacao.getCh_cadastrada_part()) + ","
		+ preparaAtributoParaBD(participacao.getCh_validada_part()) + ","
		+ preparaAtributoParaBD(participacao.getTipo_ac_part()) + ")";
		System.out.println("SQL:" + sql);
		int ok = 0;
		try {
			ok = executaAlteracao(sql);
		} catch (SQLException e) {
			//e.printStackTrace();
			String message=e.getMessage();
			if(e.getMessage().contains("participacao_pkey")){
				message = "ERRO:01 - Já existe um participacao com este id ";
			}
			ret.setSucesso(false);
			ret.setMensagem(message);
		}
		if (ok > 0) {
			ret.setSucesso(true);
			ret.setMensagem("inclusão do participacao realizada com sucesso");
		}
		
		System.out.println("Retorno:"+ ret.getMensagem());

		return ret;

	}

	private Retorno validar(Participacao participacao) {
		Retorno ret = new Retorno(true,"");
		
		if(participacao== null){
			ret.setSucesso(false);
			ret.setMensagem("participacao não foi definido, objeto inválido");
		}else if(participacao.getNome_ac_part() == null || 
				participacao.getNome_ac_part().equals("")){
			ret.setSucesso(false);
			ret.setMensagem("O campo Nome é de preenchimento obrigatório");
		}else if(participacao.getId_participacao()== null || participacao.getId_participacao().intValue()<=0){
			ret.setSucesso(false);
			ret.setMensagem("O campo Id é de preenchimento obrigatório e deve ser maior que 0!");
		}
		
		return ret;
	}

	private int executaAlteracao(String sql) throws SQLException {
		Statement stmt = con.createStatement();
		//System.out.println("SQL 2:"+sql);
		int ok = stmt.executeUpdate(sql);
		return ok;
	}
	


	public boolean excluir(Participacao participacao) throws RuntimeException {
		if (participacao == null) return false;// o id nunca vai ser nulo

		String sql = "delete from participacao"
				+ " where id='" + participacao.getId_participacao() + "' ";//TODO repetido
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

	public boolean alterar(Participacao participacao) throws RuntimeException {
		if (participacao == null)
			return false;

		
		String sql = "update participacao set "
				+ "atividade_complementar_id_atividade= '" + preparaAtributoParaBD(participacao.getAtividade_complementar_id_atividade()) + "' " 
				+ "aluno_id_alno= '"+ preparaAtributoParaBD(participacao.getAluno_id_aluno()) + "' "
				+ "certificado_part= '" + preparaAtributoParaBD(participacao.getCertificado_part()) + "' "
				+ "coordenador_ac_id_admin= '"+ preparaAtributoParaBD(participacao.getCoordenador_ac_id_admin()) + "' "
				+ "status= '"+ preparaAtributoParaBD(participacao.getStatus()) + "' " 
				+ "data_validacao_ac= '"+ preparaAtributoParaBD(participacao.getData_validaca_ac()) + "' "
				+ "nome_ac_part= '" + preparaAtributoParaBD(participacao.getNome_ac_part()) + "' "
				+ "data_inicio_ac_part= '"+ preparaAtributoParaBD(participacao.getData_inicio_ac_part()) + "' "
				+ "ch_cadastrada_part= '"+ preparaAtributoParaBD(participacao.getCh_cadastrada_part()) + "' "
				+ "ch_validada_part= '"+ preparaAtributoParaBD(participacao.getCh_validada_part()) + "' "
				+ "local_ac_part= '"+ preparaAtributoParaBD(participacao.getTipo_ac_part()) + "' "
				+ "tipo_ac_part= '"+ preparaAtributoParaBD(participacao.getTipo_ac_part()) + "' "
				+ " where id_participacao= '"+ participacao.getId_participacao() + "'";
		
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
	 * localiza uma lista de participacaos procurando pelo id(busca exata), ou pelo
	 * nome busca aproximada(com like no fim ex. joao%) O objeto participacao deve ser
	 * preenchido o campo id ou nome, os demais são desconsiderados.
	 * 
	 * @param participacao
	 * @return
	 * @throws SQLException 
	 */
	public List<Participacao> localizar(Participacao participacao) throws RuntimeException {
		ArrayList<Participacao> list = new ArrayList<Participacao>();

		String sql = "select atividade_camplementar_id_atividade, aluno_id_aluno, id_participacao, certificado_part, coord_ac_id_admin,"
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
		System.out.println("SQL localizar:"+sql);
		try {
			result = con.createStatement().executeQuery(sql);
			
			while(result.next()){
				 
				Integer atividade_comp_id = result.getInt("atividade_complementar_id_atividde");
				Integer aluno_id = result.getInt("aluno_id_aluno");
				Integer participacao_id = result.getInt("id_participacao");
				String certificado_part = result.getString("certificado_part");
				Integer coord_id = result.getInt("coord_ac_id_admin");
				String status_part = result.getString("status");
				Date data_val = result.getDate("data_validacao_ac");
				String nome_ac = result.getString("nome_ac_part");
				Date data_inicio = result.getDate("data_inicio_ac_part");
				Integer ch_cadastrada = result.getInt("ch_cadastrada_part");
				Integer ch_validada = result.getInt("ch_validada_part");
				String local_ac = result.getString("local_ac_part");
				String tipo_ac = result.getString("tipo_ac_part");	
				
				Participacao a = new Participacao(atividade_comp_id, aluno_id, participacao_id, 
						certificado_part, coord_id, status_part, data_val, nome_ac, data_inicio, 
						ch_cadastrada, ch_validada, local_ac, tipo_ac);
				
				list.add(a);
			}	
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		
		return list;
	}

	public Participacao obter(Integer id) {
		if(id == null) return null;
		String sql = "select atividade_camplementar_id_atividade, aluno_id_aluno, id_participacao, certificado_part, coord_ac_id_admin,"
				+ "status, data_validacao_ac, nome_ac_part, data_inicio_ac_part, ch_cadastrada_part, ch_validada_part, local_ac_part, tipo_ac_part "
				+ "from participacao where id_participacao=" + id + "";
	
		try {
			Statement stmt = con.createStatement();

			ResultSet resultSet = stmt.executeQuery(sql);
			if (resultSet.next()) {
				Participacao participacao = new Participacao();
				
				participacao.setAtividade_complementar_id_atividade(resultSet.getInt("atividade_complementar_id_atividade"));
				participacao.setAluno_id_aluno(resultSet.getInt("aluno_id_aluno"));
				participacao.setId_participacaoo(resultSet.getInt("id_participacao"));
				participacao.setCertificado_part(resultSet.getString("certificado_part"));
				participacao.setCoordenador_ac_id_admin(resultSet.getInt("coordenador_ac_id_admin"));
				participacao.setStatus(resultSet.getString("status"));
				participacao.setData_validaca_ac(resultSet.getDate("data_validaca_ac"));
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
