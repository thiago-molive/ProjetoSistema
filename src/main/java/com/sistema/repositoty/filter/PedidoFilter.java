package com.sistema.repositoty.filter;

import java.io.Serializable;
import java.util.Date;

import com.sistema.model.StatusPedido;

public class PedidoFilter implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long numeroDe;
	private Long numeroAte;
	private Date dataCriacaoDe;
	private Date dataCriacaoAte;
	private String vendedor;
	private String cliente;
	private StatusPedido[] status;
	
	public Long getNumeroDe() {
		return numeroDe;
	}
	public void setNumeroDe(Long filtroDe) {
		this.numeroDe = filtroDe;
	}
	public Long getNumeroAte() {
		return numeroAte;
	}
	public void setNumeroAte(Long filtroAte) {
		this.numeroAte = filtroAte;
	}
	public Date getDataCriacaoDe() {
		return dataCriacaoDe;
	}
	public void setDataCriacaoDe(Date dataCriacaoDe) {
		this.dataCriacaoDe = dataCriacaoDe;
	}
	public Date getDataCriacaoAte() {
		return dataCriacaoAte;
	}
	public void setDataCriacaoAte(Date dataCriacaoAte) {
		this.dataCriacaoAte = dataCriacaoAte;
	}
	public String getVendedor() {
		return vendedor;
	}
	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public StatusPedido[] getStatus() {
		return status;
	}
	public void setStatus(StatusPedido[] status) {
		this.status = status;
	}

}
