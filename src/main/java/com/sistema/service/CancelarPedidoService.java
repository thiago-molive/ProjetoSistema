package com.sistema.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.sistema.model.Pedido;
import com.sistema.model.StatusPedido;
import com.sistema.repositoty.Pedidos;
import com.sistema.util.jpa.Transactional;

public class CancelarPedidoService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Pedidos pedidos;
	
	@Inject
	private EstoqueService estoqueService;
	
	@Transactional
	public Pedido cancelar(Pedido pedido) {
		if(pedido.getId() != null)
		pedido = this.pedidos.porId(pedido.getId());
		else
			throw new NegocioException("O pedido não pode ser cancelado.");
		
		if(!pedido.isCancelavel()){
			throw new NegocioException("Este pedido não pode ser cancelado no estatus " + pedido.getStatus().getDescricao());
		}
		
		if(pedido.isEmitido()){
			this.estoqueService.devolverEstoque(pedido);
		}
		
		pedido.setStatus(StatusPedido.CANCELADO);
		
		pedido = pedidos.guardar(pedido);
		
		
		return pedido;
		
	}
	
	

}
