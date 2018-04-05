package br.lisianthus.controle;

import br.lisianthus.dao.DAOAluno;

import br.lisianthus.modelo.Aluno;
import br.lisianthus.modelo.Participacao;
import br.lisianthus.utils.Retorno;


public class ControladorCadastroAluno {

	private DAOAluno daoaluno;
	
	
	public ControladorCadastroAluno (){
		
		daoaluno = DAOAluno.getInstance();
	}
	
	public Retorno inserir(Aluno aluno){
		
		Retorno ret = new Retorno();
		ret = daoaluno.inserir_mod(aluno);
		return ret;
	}
	
	
}
