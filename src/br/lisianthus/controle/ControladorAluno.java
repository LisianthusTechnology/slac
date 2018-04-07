
package br.lisianthus.controle;

import java.util.List;

import br.lisianthus.dao.DAOAluno;
import br.lisianthus.modelo.Aluno;
import br.lisianthus.utils.Retorno;

public class ControladorAluno {

	private DAOAluno dao;
	public ControladorAluno(){
		dao = DAOAluno.getInstance();
	}
	
	public List<Aluno> localizar(Aluno a){
		try{
			return dao.localizar(a);
		}catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public Retorno alterar(Aluno aluno){
				
		return dao.alterar(aluno); 
	}
	
	public Aluno obter(Integer id_aluno){
		return dao.obter(id_aluno);
	}
}