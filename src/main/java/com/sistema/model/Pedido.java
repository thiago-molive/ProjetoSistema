package com.sistema.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Date dataCriacao;
	private Date dataEntrega;
	private String observacao;
	private BigDecimal valorFrete = BigDecimal.ZERO;
	private BigDecimal valorDesconto = BigDecimal.ZERO;
	private BigDecimal valorTotal = BigDecimal.ZERO;

	private StatusPedido status = StatusPedido.ORCAMENTO;
	private FormaPagamento formaPagamento = FormaPagamento.DINHEIRO;
	private Cliente cliente;
	private Usuario vendedor;
	private EnderecoEntrega enderecoEntrega;
	private List<ItemPedido> itens = new ArrayList<>();

	@Transient
	public boolean isNovo() {
		return getId() == null;
	}

	@Transient
	public boolean isExistente() {
		return !isNovo();
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao", nullable = false)
	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@Column(columnDefinition = "text")
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "data_entrega", nullable = false)
	public Date getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	@NotNull
	@Column(name = "valor_frete", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorFrete() {
		return valorFrete;
	}

	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
	}

	@NotNull
	@Column(name = "valor_desconto", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	@NotNull
	@Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}

	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cliente_id", nullable = false)
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "forma_pagamento", nullable = false, length = 20)
	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	@Embedded
	public EnderecoEntrega getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(EnderecoEntrega enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "vendedor_id", nullable = false)
	public Usuario getVendedor() {
		return vendedor;
	}

	public void setVendedor(Usuario usuario) {
		this.vendedor = usuario;
	}

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (id != other.id)
			return false;
		return true;
	}

	// -------------------------------------

	@Transient
	public BigDecimal getValorSubTotal() {
		return getValorTotal().subtract(getValorFrete()).add(getValorDesconto());
	}

	public void calcularValorTotal() {
		BigDecimal total = BigDecimal.ZERO;
		total = total.add(this.getValorFrete()).subtract(this.getValorDesconto());

		for (ItemPedido itemPedido : this.getItens()) {
			if (itemPedido.getProduto() != null && itemPedido.getProduto().getId() != null)
				total = total.add(itemPedido.getValorTotal());
		}

		this.setValorTotal(total);
	}

	public void adicionarItemVazio() {
		if (this.isOrcamento()) {

			Produto prod = new Produto();

			ItemPedido item = new ItemPedido();
			item.setProduto(prod);
			item.setPedido(this);

			this.getItens().add(0, item);

		}
	}

	@Transient
	public boolean isOrcamento() {
		return getStatus().equals(StatusPedido.ORCAMENTO);
	}

	public void removerItemVazio() {
		ItemPedido itemVazio = getItens().get(0);

		if (itemVazio != null && itemVazio.getProduto().getId() == null) {
			this.getItens().remove(0);
		}

	}

	@Transient
	public boolean isValorNegativo() {
		return this.getValorTotal().compareTo(BigDecimal.ZERO) < 0;
	}
	
	@Transient
	public boolean isEmitido(){
		return StatusPedido.EMITIDO.equals(this.getStatus());
	}
	@Transient
	public boolean isEmissivel() {
		return this.isExistente() && this.isOrcamento();
	}
	@Transient
	public boolean isCancelavel() {
		return this.isExistente() && !this.isCancelado();
	}
	@Transient
	private boolean isCancelado() {
		return StatusPedido.CANCELADO.equals(this.getStatus());
	}

}
