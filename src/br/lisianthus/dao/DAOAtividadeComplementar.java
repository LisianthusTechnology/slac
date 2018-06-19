package br.lisianthus.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.lisianthus.modelo.Aluno;
import br.lisianthus.modelo.AtividadeComplementar;
import br.lisianthus.modelo.Modalidade;
import br.lisianthus.modelo.Participacao;
import br.lisianthus.utils.Retorno;

public class DAOAtividadeComplementar {
	private static DAOAtividadeComplementar dao_ac;
	private Connection con_ac;

	private DAOAtividadeComplementar() {

		try {
			String driver_m = "org.postgresql.Driver";
			Class.forName(driver_m);
			this.con_ac = DriverManager.getConnection("jdbc:postgresql://localhost:5433/slac", "postgres", "");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage() + "DAOAtividadeComplemetar");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	public AtividadeComplementar obter(Integer id) {
		if(id == null) return null;
		String sql = "select id_atividade, descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod"
				+ " from atividade_complementar where id_atividade=" + id;
	
		try {
			Statement stmt = con_ac.createStatement();

			ResultSet resultSet = stmt.executeQuery(sql);
			if (resultSet.next()) {
				AtividadeComplementar ac = new AtividadeComplementar();
				
				ac.setId_atividade(resultSet.getInt("id_atividade"));
				ac.setDescricao_ac(resultSet.getString("descricao_ac"));
				ac.setCh_max_ac(resultSet.getInt("ch_max_ac"));
				ac.setCh_min_ac(resultSet.getInt("ch_min_ac"));
				ac.setModalidade_id_mod(resultSet.getInt("modalidade_id_mod"));
				
				return ac;
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO remover após conclusão
			e.printStackTrace();
			return null;
		}
	}
	


	public List<AtividadeComplementar> buscarAtividadeModalidade(Modalidade mod) {

		ArrayList<AtividadeComplementar> list = new ArrayList<AtividadeComplementar>();
		String sql = "select * from atividade_complementar where modalidade_id_mod =" + mod.getId_mod();
		ResultSet result = null;
		System.out.println("SQL buscarAtividadeModadalidade DAOAC:" + sql);
		try {
			result = con_ac.createStatement().executeQuery(sql);

			while (result.next()) {

				Integer id_atividade = result.getInt(1);
				Integer modalidade_id_mod = result.getInt(5);
				String descricao_ac = result.getString(2);
				Integer ch_max_ac = result.getInt(3);
				Integer ch_min_ac = result.getInt(4);

				AtividadeComplementar ac = new AtividadeComplementar(id_atividade, descricao_ac, ch_max_ac, ch_min_ac,
						modalidade_id_mod);

				list.add(ac);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}

		return list;
	}
	
	public String preparaAtributoParaBD(Object atributoValue) {
		String auxNome = "NULL";
		if (atributoValue != null) {
			auxNome = "'" + atributoValue + "'";
		}
		return auxNome;
	}
	
	public Retorno inserir(AtividadeComplementar ac) {
		Retorno ret = new Retorno(false, "erro");

		String sql = "insert into atividade_complementar(descricao_ac, ch_min_ac, ch_max_ac, modalidade_id_mod)"
				+ " values(" + "" + preparaAtributoParaBD(ac.getDescricao_ac()) + ","
				+ preparaAtributoParaBD(ac.getCh_min_ac()) + "," + preparaAtributoParaBD(ac.getCh_max_ac()) + ","
				+ preparaAtributoParaBD(ac.getModalidade_id_mod()) + ")";
		System.out.println("SQL-AtividadeComplementar:" + sql);
		int ok = 0;
		try {
			ok = executaAlteracao(sql);
		} catch (SQLException e) {
			// e.printStackTrace();
			String message = e.getMessage();
			if (e.getMessage().contains("AtividadeComplementar_pkey")) {
				message = "ERRO:01 - Já existe um Atividade com este id ";
			}
			ret.setSucesso(false);
			ret.setMensagem(message);
		}
		if (ok > 0) {
			ret.setSucesso(true);
			ret.setMensagem("inclusão da Atividade realizada com sucesso");
		}

		System.out.println("Retorno:" + ret.getMensagem());

		return ret;

	}
	
	private int executaAlteracao(String sql) throws SQLException {
		Statement stmt = con_ac.createStatement();
		// System.out.println("SQL 2:"+sql);
		int ok = stmt.executeUpdate(sql);
		return ok;
	}

	public static DAOAtividadeComplementar getInstance() {
		if (dao_ac == null) {
			dao_ac = new DAOAtividadeComplementar();
		}
		return dao_ac;
	}
}
