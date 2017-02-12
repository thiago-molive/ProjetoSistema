package com.sistema.event;

import com.sistema.model.Pedido;

//Classe que capta o evento de edicao do pedido cdi
public class PedidoEvent {

	private Pedido pedido;

	public PedidoEvent(Pedido pedido) {
		this.pedido = pedido;
	}

	public Pedido getPedido() {
		return pedido;
	}

}
