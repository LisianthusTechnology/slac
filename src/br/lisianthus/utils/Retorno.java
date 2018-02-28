package br.lisianthus.utils;

public class Retorno {
	private boolean sucesso;
	private String mensagem;
	
	
	public Retorno(boolean sucesso, String mensagem) {
		super();
		this.sucesso = sucesso;
		this.mensagem = mensagem;
	}
	
	public Retorno() {
		// TODO Auto-generated constructor stub
	}

	public boolean isSucesso() {
		return sucesso;
	}
	public void setSucesso(boolean sucesso) {
		this.sucesso = sucesso;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public void setSucesso(){
		this.sucesso = true;
	}
	public void setErro(){
		this.sucesso = false;
	}
	public boolean isErro(){
		return !isSucesso();
	}
}
