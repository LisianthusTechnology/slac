package br.lisianthus.controle;

import java.util.Date;
import java.util.List;

import br.lisianthus.dao.DAOAluno;
import br.lisianthus.modelo.Aluno;
import br.lisianthus.modelo.Coordenador;
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
	
	public List<Aluno> listaRelatorio(Date inicio, Date fim){
		return daoaluno.listaParaRelatorio(inicio, fim);
	}
	
	public Retorno inserir_data_conclusao(Aluno aluno){
		return daoaluno.inserir_data_conclusao(aluno);
	}
	
	public Retorno alterar(Aluno aluno){
				
		return daoaluno.alterar(aluno); 
	}
	
	public Aluno obter(Integer id_aluno){
		return daoaluno.obter(id_aluno);
	}
	
	public Aluno obter(Aluno loginaluno){
		
		Aluno aluno = daoaluno.obter(loginaluno);
		return aluno;
}
	public Aluno verifica_se_tem_dataconclusao(Integer id) {
		return daoaluno.verfica_data_aluno(id);
	}
}
