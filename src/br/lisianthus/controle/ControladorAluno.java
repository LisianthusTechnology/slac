package br.lisianthus.controle;

import java.util.List;

import br.lisianthus.dao.DAOAluno;
import br.lisianthus.modelo.Aluno;
import br.lisianthus.utils.Retorno;

public class ControladorAluno {

	private DAOAluno daoaluno;
	
	public ControladorAluno(){
		daoaluno = DAOAluno.getInstance();
	}
	
	public Retorno inserir(Aluno aluno){
		
		Retorno ret = new Retorno();
		ret = daoaluno.inserir(aluno);
		return ret;
	}
	
	public List<Aluno> localizar(Aluno a){
		try{
			return daoaluno.localizar(a);
		}catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public Retorno alterar(Aluno aluno){
				
		return daoaluno.alterar(aluno); 
	}
	
	public Aluno obter(Integer id_aluno){
		return daoaluno.obter(id_aluno);
	}
}
