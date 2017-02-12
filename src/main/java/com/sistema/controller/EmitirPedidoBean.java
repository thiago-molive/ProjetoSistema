package com.sistema.controller;

import java.io.Serializable;

import javax.enterprise.event.Event;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sistema.annotation.PedidoEditar;
import com.sistema.event.PedidoEvent;
import com.sistema.model.Pedido;
import com.sistema.service.EmissaoPedidoService;
import com.sistema.util.jsf.FacesUtil;

@Named
@ViewScoped
public class EmitirPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	@PedidoEditar
	private Pedido pedido;
	
	@Inject
	private EmissaoPedidoService pedidoService;
	
	@Inject
	private Event<PedidoEvent> pedidoEvent;
	
	public void emitirPedido(){
		this.pedido.removerItemVazio();
		
		try {
			this.pedido = this.pedidoService.emitir(this.pedido);
			
			//Listener CDI de event pedido
			this.pedidoEvent.fire(new PedidoEvent(this.pedido));
			
			FacesUtil.addMessage("Pedido emitido com sucesso", FacesMessage.SEVERITY_INFO);
		} finally {
			this.pedido.adicionarItemVazio();
		}
	}

}
