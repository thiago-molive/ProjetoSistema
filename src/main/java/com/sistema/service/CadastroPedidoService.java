package com.sistema.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.sistema.model.Pedido;
import com.sistema.repositoty.Pedidos;
import com.sistema.util.jpa.Transactional;

public class CadastroPedidoService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Pedidos pedidos;
	
	@Transactional
	public Pedido salvar(Pedido pedido){
		
		if(pedido.isNovo()){
			pedido.setDataCriacao(new Date());
		}
		
		pedido.calcularValorTotal();
		
		if(!pedido.isOrcamento()){
			throw new NegocioException("Não foi possivel alterar esse pedido no status " + pedido.getStatus().getDescricao().toLowerCase());
		}
		
		if (pedido.getItens().isEmpty()) {
			throw new NegocioException("Deve ter pelo menos um produto no pedido.");
		}
		
		if (pedido.isValorNegativo()) {
			throw new NegocioException("O valor não pode ser negativo");
		}
		
		pedido = this.pedidos.guardar(pedido);
		
		return pedido;
	}

}
