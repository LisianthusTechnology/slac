package br.lisianthus.modelo;

public class AtividadeComplementar {
	private Integer id_atividade;
	private Integer modalidade_id_mod;
	private String descricao_ac;
	private Integer ch_max_ac;
	private Integer ch_min_ac;
	
	public AtividadeComplementar(Integer id_atividade, String descricao_ac, Integer ch_max_ac, Integer ch_min_ac,
			Integer modalidade_id_mod) {
		
		setId_atividade(id_atividade);
		setDescricao_ac(descricao_ac);
		setCh_max_ac(ch_max_ac);
		setCh_min_ac(ch_min_ac);
		setModalidade_id_mod(modalidade_id_mod);
		
	}
	public Integer getId_atividade() {
		return id_atividade;
	}
	public void setId_atividade(Integer id_atividade) {
		this.id_atividade = id_atividade;
	}
	public Integer getModalidade_id_mod() {
		return modalidade_id_mod;
	}
	public void setModalidade_id_mod(Integer modalidade_id_mod) {
		this.modalidade_id_mod = modalidade_id_mod;
	}
	public String getDescricao_ac() {
		return descricao_ac;
	}
	public void setDescricao_ac(String descricao_ac) {
		this.descricao_ac = descricao_ac;
	}
	public Integer getCh_max_ac() {
		return ch_max_ac;
	}
	public void setCh_max_ac(Integer ch_max_ac) {
		this.ch_max_ac = ch_max_ac;
	}
	public Integer getCh_min_ac() {
		return ch_min_ac;
	}
	public void setCh_min_ac(Integer ch_min_ac) {
		this.ch_min_ac = ch_min_ac;
	}	
}
