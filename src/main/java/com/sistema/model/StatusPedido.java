package com.sistema.model;

public enum StatusPedido {
	ORCAMENTO("Orçamento"), EMITIDO("Emitido"), CANCELADO("Cancelado");
	
	private String descricao;

	StatusPedido(String desc) {
		this.descricao = desc;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
