package br.lisianthus.modelo;

public class Coordenador {
	
	private Integer id_admin;
	private String nome_admin, login, senha;
	
	public Coordenador (){
		
	}
	
	public Coordenador (Integer idadmin, String nomeadmin, String loginadmin, String senhaadmin){
		id_admin = idadmin;
		nome_admin = nomeadmin;
		login = loginadmin;
		senha = senhaadmin;
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
