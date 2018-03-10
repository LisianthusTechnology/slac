package br.lisianthus.teste;

import java.util.List;

import br.lisianthus.dao.DAOModalidade;
import br.lisianthus.modelo.Modalidade;

public class testeDAOModalidade {
	
	public static void main(String[] args){
		DAOModalidade dao_m = DAOModalidade.getInstance();
		
		System.out.println("------TESTE COM MODALIDADE LOCALIZAR-----");
		Modalidade mod = new Modalidade();
		List<Modalidade> moda = dao_m.localizar(mod);
		
		System.out.println(moda);
		
		
	}
}
