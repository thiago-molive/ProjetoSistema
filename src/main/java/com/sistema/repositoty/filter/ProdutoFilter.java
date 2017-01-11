package com.sistema.repositoty.filter;

import java.io.Serializable;

import com.sistema.validation.CodigoNumero;

public class ProdutoFilter implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String numero;
	private String nome;
	
	@CodigoNumero
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero == null ? null : numero.toUpperCase();
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	

}
