package br.lisianthus.modelo;

public class Coordenador {
	
	Integer id;
	String nome;
	String login;
	String senha;
	private Integer id_admin;
	private String nome_admin;
	
	public Coordenador(String nome, String login, String senha){
		this.nome = nome;
		this.login = login;
		this.senha = senha;
	}
	
	public Coordenador(String login, String senha){
		this.login = login;
		this.senha = senha;
	}
	
	public Coordenador (Integer idadmin, String nomeadmin, String loginadmin, String senhaadmin){
		id_admin = idadmin;
		nome_admin = nomeadmin;
		login = loginadmin;
		senha = senhaadmin;
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

	public Integer getId_admin() {
		return id_admin;
	}

	public void setId_admin(Integer id_admin) {
		this.id_admin = id_admin;
	}

	public String getNome_admin() {
		return nome_admin;
	}

	public void setNome_admin(String nome_admin) {
		this.nome_admin = nome_admin;
	}
}
