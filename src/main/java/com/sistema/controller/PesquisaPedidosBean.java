package com.sistema.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sistema.model.Pedido;
import com.sistema.model.StatusPedido;
import com.sistema.repositoty.Pedidos;
import com.sistema.repositoty.filter.PedidoFilter;

@Named
@ViewScoped
public class PesquisaPedidosBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Pedidos pedidos;
	
	private PedidoFilter filtro;
	private static List<Pedido> pedidosFiltrados;

	public PesquisaPedidosBean() {
		pedidosFiltrados = new ArrayList<>();
		filtro = new PedidoFilter();
	}
	
	public void pesquisar(){
		pedidosFiltrados = pedidos.pedidoFiltrados(filtro);
	}
	
	public StatusPedido[] getStatusPedido(){
		return StatusPedido.values();
	}
	public List<Pedido> getProdutosFiltrados() {
		return pedidosFiltrados;
	}

	public PedidoFilter getFiltro() {
		return filtro;
	}
	
}
