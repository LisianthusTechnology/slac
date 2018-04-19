package br.lisianthus.controle;

import java.util.List;

import br.lisianthus.dao.DAOParticipacao;
import br.lisianthus.modelo.Aluno;
import br.lisianthus.modelo.AtividadeComplementar;
import br.lisianthus.modelo.Modalidade;
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
		ret = dao.inserir(partComp);
		return ret;
	}
	
	private Participacao calcularChValida(Participacao part) {
				AtividadeComplementar ac = new AtividadeComplementar();
				ControladorAtividadeComplementar contAC = new ControladorAtividadeComplementar();
				ac = contAC.obter(part.getAtividade_complementar_id_atividade());
				
				//VERIFICAR ESTA REGRA
				if((ac.getCh_max_ac() != 0) || (ac.getCh_min_ac() != 0)){//para saber se a atividade tem ou não carga horária mínima ou máxima
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
	
	private boolean VerificaCadastroModalidade(Participacao part){
		Aluno aluno = new Aluno();
		Modalidade mod = new Modalidade();
		//preciso de todas as participacoes cadastradas pelo aluno onde o id for o da modalidade
		//select * from participacao where id_aluno = aluno.getId e/&/, id_modalidade = modalidade.get_id
		//quando for para chamar o DAO AC é necessário que a lista retornada volte pra cá e daqui seja passda para o daopart
		//avisar que já existem muitas atividades em uma mesma modalidade e
		//que é necessário o cadastro em mais de uma modalidade
		
		return false;
	}

	public List<Participacao> listarParticipacaoConsulta(Participacao part){
		return dao.localizarConsulta(part);
	}
	
	public List<Participacao> buscarParticipacao(Participacao part){
		return dao.localizar(part);
	}
	
	public Retorno alterarParticipacao(Participacao part){
		return dao.alterar(part);
	}
	
	public Participacao obterParticipacao(Integer id_part){
		return dao.obter(id_part);
	}
	
	public void obterModalidade(int id_ac){
		
	}
}
