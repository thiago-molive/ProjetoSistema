package com.sistema.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

@ManagedBean
@ViewScoped
public class PesquisaProdutosBean {
	
	private static List<Integer> produtosFiltrados;

	
	public PesquisaProdutosBean() {
		System.out.println("Funcionando");
		produtosFiltrados = new ArrayList<>();
		for(int i=0; i < 50; i++){
			produtosFiltrados.add(i);
		}
	}

	public List<Integer> getProdutosFiltrados() {
		return produtosFiltrados;
	}
	
}
