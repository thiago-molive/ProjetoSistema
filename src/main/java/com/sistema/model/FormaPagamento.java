package com.sistema.model;

public enum FormaPagamento {
	DINHEIRO("Dinheiro"), CARTAO_CREDITO("Cartão de Crédito"), CARTAO_DEBITO("Cartão de Débito"), CHEQUE(
			"Cheque"), BOLETO_BANCARIO("Boleto Bancário"), DEPOSITO_BANCARIO("Depósito Bancário");

	private String desc;

	FormaPagamento(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

}
