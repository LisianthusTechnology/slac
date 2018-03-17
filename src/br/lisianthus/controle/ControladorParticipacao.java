package br.lisianthus.controle;

import br.lisianthus.dao.DAOParticipacao;
import br.lisianthus.modelo.Participacao;
import br.lisianthus.utils.Retorno;

public class ControladorParticipacao {
	private DAOParticipacao dao;
	//Retorno ret = new Retorno();
	public ControladorParticipacao() {
		dao = DAOParticipacao.getInstance();
	}
	
	public Retorno inserir(Participacao part){
		
		//dao.inserir(part);
		return dao.inserir(part);
	}

}
