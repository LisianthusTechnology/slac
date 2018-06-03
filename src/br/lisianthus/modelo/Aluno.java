package br.lisianthus.modelo;

import java.util.Date;

public class Aluno {

	private Integer id_aluno, matricula, ano_ingresso, coord_ac_id;
	private Long cpf;
	private String nome_aluno, nome, nome_admin, senha, email;
	private boolean permissao;
	public Date data_carga_total_part;
	public Date data_conclusao_carga;
	
	public Aluno(){
		
	}
	
	public Aluno(Integer idaluno, Integer matri, Integer anoingresso, Integer coordid, Long cpf_aluno, String nomealuno, String senha_aluno, String email_a, boolean permissao_aluno){
		id_aluno = idaluno;
		matricula = matri;
		ano_ingresso = anoingresso;
		coord_ac_id = coordid;
		cpf = cpf_aluno;
		nome_aluno = nomealuno;
		senha = senha_aluno;
		email = email_a;
		permissao = permissao_aluno;
		
	}
	
	public Aluno (String nomeadmin, String nomealuno, int matricula_aluno, int anoingresso, Date dataconlusao_carga){
		nome_admin = nomeadmin;
		nome = nomealuno;
		matricula = matricula_aluno;
		ano_ingresso = anoingresso;
		data_conclusao_carga = dataconlusao_carga;// 
	}
	
	//USANDO ESSE METODO PRA PEGAR O ID DO ALUNO NA SERVLET E DEPOIS FAZER ALTERAÇÃO PARA SALVAR A CARGA HORÁRIA TOTAL
	public Aluno(Integer idaluno){
		id_aluno = idaluno;
	}
	
	public Aluno(Date data_carga_total_part1){
		this.data_carga_total_part = data_carga_total_part1;
	}
	
	public Aluno(Long cpf2, String password) {
		this.cpf = cpf2;
		this.senha = password;
		
	}

	public Date getData_carga_total_part() {
		return data_carga_total_part;
	}
	public void setData_carga_total_part(Date data_carga_total_part) {
		this.data_carga_total_part = data_carga_total_part;
	}
	public Integer getId_aluno() {
		return id_aluno;
	}
	public void setId_aluno(Integer id_aluno) {
		this.id_aluno = id_aluno;
	}
	public Integer getMatricula() {
		return matricula;
	}
	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}
	public Integer getAno_ingresso() {
		return ano_ingresso;
	}
	public void setAno_ingresso(Integer ano_ingresso) {
		this.ano_ingresso = ano_ingresso;
	}
	public Integer getCoord_ac_id() {
		return coord_ac_id;
	}
	public void setCoord_ac_id(Integer coord_ac_id) {
		this.coord_ac_id = coord_ac_id;
	}
	public Long getCpf() {
		return cpf;
	}
	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}
	public String getNome_aluno() {
		return nome_aluno;
	}
	public void setNome_aluno(String nome_aluno) {
		this.nome_aluno = nome_aluno;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isPermissao() {
		return permissao;
	}
	public void setPermissao(boolean permissao) {
		this.permissao = permissao;
	}

	public String getNome_admin() {
		return nome_admin;
	}

	public void setNome_admin(String nome_admin) {
		this.nome_admin = nome_admin;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	public Date getData_conclusao_carga() {
		return data_conclusao_carga;
	}

	public void setData_conclusao_carga(Date data_conclusao) {
		this.data_conclusao_carga = data_conclusao;
	}


	
}
