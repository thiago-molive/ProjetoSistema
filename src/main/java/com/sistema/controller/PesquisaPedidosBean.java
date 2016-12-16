package com.sistema.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class PesquisaPedidosBean {
	
	private static List<Integer> pedidosFiltrados;

	
	public PesquisaPedidosBean() {
		System.out.println("Funcionando");
		pedidosFiltrados = new ArrayList<>();
		for(int i=0; i < 50; i++){
			pedidosFiltrados.add(i);
		}
	}

	public List<Integer> getProdutosFiltrados() {
		return pedidosFiltrados;
	}
	
}
