package com.sistema.controller;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.sistema.service.NegocioException;

@Named
@RequestScoped
public class CadastroPedidoBean {
	
	List<Integer> itens;

	public CadastroPedidoBean() {
		itens = new ArrayList<>();
		itens.add(1);
	}

	public List<Integer> getItens() {
		return itens;
	}
	
	public void salvar() {
		throw new NegocioException("Não pode ser salvo, pois não há implementação.");
	}
	
}
