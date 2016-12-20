package com.sistema.controller;

import javax.enterprise.inject.Model;

@Model
public class CadastroProdutoBean {
	
	public void salvar(){
		throw new RuntimeException("Teste de Exceção.");
	}
}
