package br.lisianthus.modelo;

public class Modalidade {
	
	private Integer id_mod;
	private String nome_mod;
	
	public Modalidade(){
		
	}
	
	public Modalidade(Integer idmod, String nomemod){
		id_mod = idmod;
		nome_mod = nomemod;
	}
	
	public Integer getId_mod() {
		return id_mod;
	}
	public void setId_mod(Integer id_mod) {
		this.id_mod = id_mod;
	}
	public String getNome_mod() {
		return nome_mod;
	}
	public void setNome_mod(String nome_mod) {
		this.nome_mod = nome_mod;
	}
	

}
