package br.lisianthus.controle;

import java.util.List;

import br.lisianthus.dao.DAOParticipacao;
import br.lisianthus.modelo.Modalidade;

public class Controle {
	private DAOParticipacao dao;
	public Controle() {
		dao = DAOParticipacao.getInstance();
	}
	
	public List<Modalidade> localizar(Modalidade a) throws RuntimeException{
		try{
			return dao.localizar(a);
		}catch(RuntimeException e){
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public Amigo obter(Integer id){
		return dao.obter(id);
	}
}
