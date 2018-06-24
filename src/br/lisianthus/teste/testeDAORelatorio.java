package br.lisianthus.teste;

import java.util.Date;

import br.lisianthus.controle.ControladorAluno;
import br.lisianthus.modelo.Aluno;
import br.lisianthus.visao.ServletCadastroEventos;
import net.sf.jasperreports.engine.JRException;

public class testeDAORelatorio {
	
	public static void main(String[] args) throws JRException{
		
		Aluno aluno = new Aluno();
		ControladorAluno ct = new ControladorAluno();
		ServletCadastroEventos sv = new ServletCadastroEventos();
		/*
		aluno.setMatricula(555666);
		aluno.setCpf(new Long("06096526366"));
		aluno.setNome_aluno("Gleycy Kelly");
		aluno.setSenha("333");
		aluno.setEmail("gleycy@kelly.com");
		aluno.setAno_ingresso(2014);
		aluno.setPermissao(true);
		aluno.setData_carga_total_part(new Date(22-05-2018));
		aluno.setCoord_ac_id(1);
		*/
		sv.init();
		//sv.gerarRelatorioParticipacao();
		//ct.inserir(aluno);
		
	}

}
