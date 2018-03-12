package br.lisianthus.controle;

import br.lisianthus.dao.DAOParticipacao;
import br.lisianthus.modelo.Participacao;
import br.lisianthus.utils.Retorno;

public class ControladorParticipacao {
	private DAOParticipacao dao;
	
	public ControladorParticipacao() {
		dao = DAOParticipacao.getInstance();
	}
	
	public Retorno inserir(Participacao part){
		Retorno ret = new Retorno();
		dao.inserir(part);
		return ret;
	}

}
