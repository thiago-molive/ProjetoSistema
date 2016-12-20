package com.sistema.controller;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class PesquisaPedidosBean {
	
	private static List<Integer> pedidosFiltrados;

	
	public PesquisaPedidosBean() {
		pedidosFiltrados = new ArrayList<>();
		for(int i=0; i < 50; i++){
			pedidosFiltrados.add(i);
		}
	}

	public List<Integer> getProdutosFiltrados() {
		return pedidosFiltrados;
	}
	
}
