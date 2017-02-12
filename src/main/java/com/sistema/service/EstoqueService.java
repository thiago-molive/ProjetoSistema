package com.sistema.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.sistema.model.ItemPedido;
import com.sistema.model.Pedido;
import com.sistema.repositoty.Pedidos;
import com.sistema.util.jpa.Transactional;

public class EstoqueService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Pedidos pedidos;
	
	@Transactional
	public void baixarItensEstoque(Pedido pedido) {
		pedido = this.pedidos.porId(pedido.getId());
		
		for (ItemPedido item : pedido.getItens()) {
			item.getProduto().baixarEstoque(item.getQuantidade());
		}
	}

	public void devolverEstoque(Pedido pedido) {
		pedido = this.pedidos.porId(pedido.getId());
		
		for (ItemPedido item : pedido.getItens()) {
			item.getProduto().addEstoque(item.getQuantidade());
		}
	}
	
	

}
