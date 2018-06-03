package br.lisianthus.modelo;

public class Coordenador {
	
	Integer id;
	String nome;
	String login;
	String senha;
	
	public Coordenador(String nome, String login, String senha){
		this.nome = nome;
		this.login = login;
		this.senha = senha;
	}
	
	public Coordenador(String login, String senha){
		this.login = login;
		this.senha = senha;
	}
	
	public Coordenador() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
}
