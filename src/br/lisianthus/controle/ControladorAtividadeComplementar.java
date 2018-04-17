package br.lisianthus.controle;

import java.util.List;

import br.lisianthus.dao.DAOAtividadeComplementar;
import br.lisianthus.modelo.AtividadeComplementar;
import br.lisianthus.modelo.Modalidade;

public class ControladorAtividadeComplementar {
	
	private DAOAtividadeComplementar dao;
	public ControladorAtividadeComplementar() {
		dao = DAOAtividadeComplementar.getInstance();
	}
	
	public List<AtividadeComplementar> localizar(Modalidade mod) throws RuntimeException{
		try{
			return dao.buscarAtividadeModalidade(mod);
		}catch(RuntimeException e){
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public AtividadeComplementar obter(Integer id_ac){
		return dao.obter(id_ac);
	}
	
}
