package br.lisianthus.controle;

import java.util.List;

import br.lisianthus.dao.DAOModalidade;

import br.lisianthus.modelo.Modalidade;

public class ControladorModalidade {
	private DAOModalidade dao;
	public ControladorModalidade() {
		dao = DAOModalidade.getInstance();
	}
	
	public List<Modalidade> localizar(Modalidade a) throws RuntimeException{
		try{
			return dao.localizar(a);
		}catch(RuntimeException e){
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public Modalidade obter(Integer id){
		return dao.obter(id);
	}
}
