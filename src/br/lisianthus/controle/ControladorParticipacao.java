package br.lisianthus.controle;

import br.lisianthus.dao.DAOParticipacao;
import br.lisianthus.modelo.AtividadeComplementar;
import br.lisianthus.modelo.Participacao;
import br.lisianthus.utils.Retorno;

public class ControladorParticipacao {
	private DAOParticipacao dao;
	//Retorno ret = new Retorno();
	public ControladorParticipacao() {
		dao = DAOParticipacao.getInstance();
	}
	
	public Retorno inserir(Participacao part){
		
		Retorno ret = new Retorno();
		Participacao partComp = new Participacao();
		partComp = calcularChValida(part);
		dao.inserir(partComp);
		return ret;
	}

	private Participacao calcularChValida(Participacao part) {
		AtividadeComplementar ac = new AtividadeComplementar();
		ControladorAtividadeComplementar contAC = new ControladorAtividadeComplementar();
		ac = contAC.obter(part.getAtividade_complementar_id_atividade());
		
		//VERIFICAR ESTA REGRA
		if((ac.getCh_max_ac() != null) || (ac.getCh_min_ac() != null)){//para saber se a atividade tem ou não carga horária mínima ou máxima
			Integer chMax = ac.getCh_max_ac();
			Integer chMin = ac.getCh_min_ac();
			if(chMax >= part.getCh_cadastrada_part() && chMin <= part.getCh_cadastrada_part()){//para saber se a carga horária da atividade complemntar é maior ou igual a que foi cadastrada
				part.setCh_validada_part(part.getCh_cadastrada_part());//se sim, a carga horaria cadastrada pode ficar
			}else{
				part.setCh_validada_part(chMax);//se não a carga horaria que fica é a máxima para aquela atividade
			}
		}else{//se não tem ch min e max é a do certificado, cadastrada pelo aluno
			part.setCh_validada_part(part.getCh_cadastrada_part());
		}
		
		return part;
	}

}
