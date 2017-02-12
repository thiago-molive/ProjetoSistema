package com.sistema.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.sistema.model.Pedido;
import com.sistema.model.StatusPedido;
import com.sistema.repositoty.Pedidos;
import com.sistema.util.jpa.Transactional;

public class EmissaoPedidoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroPedidoService cadastroPedidoService;
	@Inject
	private EstoqueService estoqueService;
	//não vejo a necessidade disso
	@Inject
	private Pedidos pedidos;;
	
	
	@Transactional
	public Pedido emitir(Pedido pedido) {
		
		pedido = cadastroPedidoService.salvar(pedido);
		
		//se nao for emissivel
		if(!pedido.isEmissivel()){
			throw new NegocioException("Pedido não pode ser emitido com statis " + pedido.getStatus().getDescricao());
		}
		
		this.estoqueService.baixarItensEstoque(pedido);
		
		pedido.setStatus(StatusPedido.EMITIDO);
		
		//nao vejo a necessidade disso
		pedido = this.pedidos.guardar(pedido);
		
		return pedido;
	}

}
