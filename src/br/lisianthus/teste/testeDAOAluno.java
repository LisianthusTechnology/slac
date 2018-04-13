
package br.lisianthus.teste;

import java.util.List;

import br.lisianthus.dao.DAOAluno;

import br.lisianthus.modelo.Aluno;
import br.lisianthus.modelo.Modalidade;
import br.lisianthus.utils.Retorno;


public class testeDAOAluno {
	public static void main(String[] args){
		DAOAluno dao_a = DAOAluno.getInstance();
		
		System.out.println("------TESTE COM ALUNO LOCALIZAR-----");
		Aluno aluno = new Aluno();
		
		//List<Aluno> al = dao_a.localizar(aluno);
		aluno = dao_a.obter(2);
		aluno.setPermissao(true);
		Retorno ret = dao_a.alterar(aluno);
		
		System.out.println(aluno.isPermissao());
		
	}
}

