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
import com.sistema.service.CancelarPedidoService;
import com.sistema.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CancelarPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	@PedidoEditar
	private Pedido pedido;
	
	@Inject
	private CancelarPedidoService cancelarPedidoService;
	
	@Inject
	private Event<PedidoEvent> pedidoEvent;
	
	
	
	public void cancelarPedido(){
		this.pedido = this.cancelarPedidoService.cancelar(this.pedido);
		
		//lança um event listener de alteração de pedido
		this.pedidoEvent.fire(new PedidoEvent(this.pedido));
		
		FacesUtil.addMessage("Pedido Cancelado com sucesso.", FacesMessage.SEVERITY_INFO);
	}

}
