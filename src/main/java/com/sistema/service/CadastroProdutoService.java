package com.sistema.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.sistema.model.Produto;
import com.sistema.repositoty.Produtos;
import com.sistema.util.jpa.Transactional;

public class CadastroProdutoService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Produtos produtos;
	
	@Transactional
	public Produto salvar(Produto produto){
		Produto produtoExiste = produtos.porNumero(produto.getNumero());
		
		if(produtoExiste != null && !(produtoExiste.equals(produto))){
			throw new NegocioException("Produto já existe");
		}
		return produtos.salvar(produto);
	}
	
}
