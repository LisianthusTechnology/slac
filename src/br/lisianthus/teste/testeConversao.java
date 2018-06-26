package br.lisianthus.teste;

import br.lisianthus.utils.Mensagens;

public class testeConversao {
	
	public static void main(String[] args){
	
		Mensagens msg = new Mensagens();
		String id = "12015001485";
		long idlong = id != null && !id.equals("") ? Long.parseLong(id) : null;
		System.out.println(idlong);
		//return idlong;
		
		String id_int = "2010";
		Integer idint = id_int != null && !id_int.equals("") ? Integer.parseInt(id_int) : null;
		System.out.println(idint);
	}

}
