package com.sistema.controller;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sistema.model.Produto;
import com.sistema.repositoty.Produtos;
import com.sistema.repositoty.filter.ProdutoFilter;

@Named
@RequestScoped
public class PesquisaProdutosBean {
	
	@Inject
	private Produtos produtos;
	
	private ProdutoFilter filtro;
	
	private List<Produto> produtosFiltrados;
	
	public PesquisaProdutosBean() {
		filtro = new ProdutoFilter();
	}

	public void pesquisar(){
		produtosFiltrados = produtos.filtrados(filtro);
	}
	
	public ProdutoFilter getFiltro() {
		return filtro;
	}

	public List<Produto> getProdutosFiltrados() {
		return produtosFiltrados;
	}
	
	
	
}
