package br.lisianthus.teste;

import java.util.Date;

import br.lisianthus.dao.DAOParticipacao;
import br.lisianthus.modelo.Participacao;
import javafx.scene.chart.PieChart.Data;

public class testeDAOParticipacao {

	public static void main(String[] args){
		DAOParticipacao daop = DAOParticipacao.getInstance();
		

		
		System.out.println("------TESTE COM PARTICIPACAO INSERIR-----");
		Participacao part = new Participacao();
		
		part.setAtividade_complementar_id_atividade(1);
		part.setAluno_id_aluno(1);
		part.setId_participacaoo(3);
		part.setCertificado_part("teste 2");
		part.setCoordenador_ac_id_admin(1);
		part.setStatus("a validar");
		
		String dataString = "10/02/2013"; //Variavel que você recebe como parametro .
		//Data date = new Data(dataString,0); //A classe Date cria a data a partir da sua String, porem ela deve estar no formato "MM/dd/yyyy", "MM/dd/yy";
		//Ou você pode criar sua data passando valor por valor
		Date data = new Date(); //Variavel do tipo Date que você usa para inserir no banco
		
		String[] dt = dataString.split("/"); //Aqui eu crio um array a partir dos valores vindos do parametro separados pela '/'.
		data.setDate(Integer.parseInt(dt[1])); //Aqui eu passo o dia, mes e ano pelos métodos do Date
		data.setMonth(Integer.parseInt(dt[0]));
		data.setYear(Integer.parseInt(dt[2]));
		part.setData_validaca_ac(data);
		
		part.setNome_ac_part("teste part 3");
		
		
		String dataString2 = "10/02/2013"; //Variavel que você recebe como parametro .
		//Data date = new Data(dataString,0); //A classe Date cria a data a partir da sua String, porem ela deve estar no formato "MM/dd/yyyy", "MM/dd/yy";
		//Ou você pode criar sua data passando valor por valor
		Date data2 = new Date(); //Variavel do tipo Date que você usa para inserir no banco
		
		String[] dt2 = dataString2.split("/"); //Aqui eu crio um array a partir dos valores vindos do parametro separados pela '/'.
		data2.setDate(Integer.parseInt(dt2[1])); //Aqui eu passo o dia, mes e ano pelos métodos do Date
		data2.setMonth(Integer.parseInt(dt2[0]));
		data2.setYear(Integer.parseInt(dt2[2]));
		part.setData_inicio_ac_part(data2);
		
		
		part.setCh_cadastrada_part(40);
		part.setCh_validada_part(20);
		part.setLocal_ac_part("Anapolis");
		part.setTipo_ac_part("distancia");
		
		System.out.println(part.getLocal_ac_part());
		
		if(daop != null){
		
			daop.inserir(part);
		}
		
		
	}
}
