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
	//	Retorno ret = new Retorno();
				
				Participacao partComp = new Participacao();
				partComp = calcularChValida(part);
				return dao.inserir(partComp);
	}
	
	private Participacao calcularChValida(Participacao part) {
				AtividadeComplementar ac = new AtividadeComplementar();
				ControladorAtividadeComplementar contAC = new ControladorAtividadeComplementar();
				ac = contAC.obter(part.getAtividade_complementar_id_atividade());
				
				//VERIFICAR ESTA REGRA
				if((ac.getCh_max_ac() != 0) || (ac.getCh_min_ac() != 0)){//para saber se a atividade tem ou n�o carga hor�ria m�nima ou m�xima
					Integer chMax = ac.getCh_max_ac();
					Integer chMin = ac.getCh_min_ac();
					System.out.println("Cargas hor�rias:"+chMax+" "+chMin);
					if(chMax >= part.getCh_cadastrada_part() && chMin <= part.getCh_cadastrada_part()){//para saber se a carga hor�ria da atividade complemntar � maior ou igual a que foi cadastrada
						part.setCh_validada_part(part.getCh_cadastrada_part());//se sim, a carga horaria cadastrada pode ficar
					}else{
						part.setCh_validada_part(chMax);//se n�o a carga horaria que fica � a m�xima para aquela atividade
					}
				}else{//se n�o tem ch min e max � a do certificado, cadastrada pelo aluno
					part.setCh_validada_part(part.getCh_cadastrada_part());
				}
				
				return part;
			}

}
