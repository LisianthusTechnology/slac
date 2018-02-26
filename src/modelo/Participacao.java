package modelo;

import sun.util.calendar.BaseCalendar.Date;

/**
 * 
 * @author Gleycy
 * As variaveis estao com os mesmos nomes do banco de dados com o seguinte padra nome_da_tabela_nome_do_campo
 */
public class Participacao {
	private Integer atividade_complementar_id_atividade;
	private Integer aluno_id_aluno;
	private Integer id_participacao;
	private Integer coordenador_ac_id_admin;
	private String certificado_part;
	private String status;
	private Date data_validaca_ac;
	private String nome_ac_part;
	private Date data_inicio_ac_part;
	private Integer ch_cadastrada_part;
	private Integer ch_validada_part;
	private String local_ac_part;
	private String tipo_ac_part;
	
	
	public Participacao(Integer atividade_complementar_id_atividade, Integer aluno_id_aluno, Integer id_participacao, 
			String certificado_part, Integer coordenador_ac_id_admin,
			String status, Date data_validacao_ac, String nome_ac_part, Date data_inicio_ac_part, 
			Integer ch_cadastrada_part, Integer ch_validada_part, String local_ac_part, String tipo_ac_part){
		
		atividade_complementar_id_atividade = atividade_complementar_id_atividade;
		aluno_id_aluno = aluno_id_aluno;
		id_participacao = id_participacao;
		coordenador_ac_id_admin = coordenador_ac_id_admin;
		certificado_part = certificado_part;
		status = status;
		data_validaca_ac = data_validacao_ac;
		nome_ac_part = nome_ac_part;
		data_inicio_ac_part = data_inicio_ac_part;
		ch_cadastrada_part = ch_cadastrada_part;
		ch_validada_part = ch_validada_part;
		local_ac_part = local_ac_part;
		tipo_ac_part = tipo_ac_part;
			
	}
	
	public Integer getAtividade_complementar_id_atividade() {
		return atividade_complementar_id_atividade;
	}
	public void setAtividade_complementar_id_atividade(Integer atividade_complementar_id_atividade) {
		this.atividade_complementar_id_atividade = atividade_complementar_id_atividade;
	}
	public Integer getAluno_id_aluno() {
		return aluno_id_aluno;
	}
	public void setAluno_id_aluno(Integer aluno_id_aluno) {
		this.aluno_id_aluno = aluno_id_aluno;
	}
	public Integer getId_participacao() {
		return id_participacao;
	}
	public void setId_participacaoo(Integer id_participacaoo) {
		this.id_participacao = id_participacaoo;
	}
	public Integer getCoordenador_ac_id_admin() {
		return coordenador_ac_id_admin;
	}
	public void setCoordenador_ac_id_admin(Integer coordenador_ac_id_admin) {
		this.coordenador_ac_id_admin = coordenador_ac_id_admin;
	}
	public String getCertificado_part() {
		return certificado_part;
	}
	public void setCertificado_part(String certificado_part) {
		this.certificado_part = certificado_part;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getData_validaca_ac() {
		return data_validaca_ac;
	}
	public void setData_validaca_ac(Date data_validaca_ac) {
		this.data_validaca_ac = data_validaca_ac;
	}
	public String getNome_ac_part() {
		return nome_ac_part;
	}
	public void setNome_ac_part(String nome_ac_part) {
		this.nome_ac_part = nome_ac_part;
	}
	public Date getData_inicio_ac_part() {
		return data_inicio_ac_part;
	}
	public void setData_inicio_ac_part(Date data_inicio_ac_part) {
		this.data_inicio_ac_part = data_inicio_ac_part;
	}
	public Integer getCh_cadastrada_part() {
		return ch_cadastrada_part;
	}
	public void setCh_cadastrada_part(Integer ch_cadastrada_part) {
		this.ch_cadastrada_part = ch_cadastrada_part;
	}
	public Integer getCh_validada_part() {
		return ch_validada_part;
	}
	public void setCh_validada_part(Integer ch_validada_part) {
		this.ch_validada_part = ch_validada_part;
	}
	public String getLocal_ac_part() {
		return local_ac_part;
	}
	public void setLocal_ac_part(String local_ac_part) {
		this.local_ac_part = local_ac_part;
	}
	public String getTipo_ac_part() {
		return tipo_ac_part;
	}
	public void setTipo_ac_part(String tipo_ac_part) {
		this.tipo_ac_part = tipo_ac_part;
	}
	
	
}
