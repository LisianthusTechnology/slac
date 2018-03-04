package br.lisianthus.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.lisianthus.modelo.AtividadeComplementar;
import br.lisianthus.modelo.Modalidade;
import br.lisianthus.modelo.Participacao;

public class DAOAtividadeComplementar {
	private static DAOAtividadeComplementar dao_ac;
	private Connection con_ac;
	
	private DAOAtividadeComplementar(){
		
		try{
			String driver_m = "org.postgresql.Driver";
			Class.forName(driver_m);
			this.con_ac = DriverManager.getConnection("jdbc:postgresql://localhost:5433/slac", "postgres", "");
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage()+"DAOAtividadeComplemetar");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public List<AtividadeComplementar> buscarAtividadeModalidade(Modalidade mod){
		ArrayList<AtividadeComplementar> list = new ArrayList<AtividadeComplementar>();
		String sql = "select * from atividade_complementar where modalide_id_mod =" + mod.getId_mod();
		ResultSet result = null;
		System.out.println("SQL buscarAtividadeModadalidade DAOAC:"+sql);
		try {
			result = con_ac.createStatement().executeQuery(sql);
			
			while(result.next()){
			
				Integer  id_atividade = result.getInt(1);
				Integer  modalidade_id_mod = result.getInt(5);
				String descricao_ac = result.getString(2);
				Integer  ch_max_ac = result.getInt(3);
				Integer  ch_min_ac = result.getInt(4);
				
				AtividadeComplementar ac = new AtividadeComplementar(id_atividade, descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod);
				
				list.add(ac);
			}	
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		
		return list;
	} 
	
	public static DAOAtividadeComplementar getInstance(){
		if(dao_ac == null){
			dao_ac = new DAOAtividadeComplementar();
		}
		return dao_ac;
	}
}
