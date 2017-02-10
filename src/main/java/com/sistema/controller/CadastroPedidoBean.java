package com.sistema.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.sistema.model.Cliente;
import com.sistema.model.EnderecoEntrega;
import com.sistema.model.FormaPagamento;
import com.sistema.model.ItemPedido;
import com.sistema.model.Pedido;
import com.sistema.model.Produto;
import com.sistema.model.Usuario;
import com.sistema.repositoty.Clientes;
import com.sistema.repositoty.Produtos;
import com.sistema.repositoty.Usuarios;
import com.sistema.service.CadastroPedidoService;
import com.sistema.service.NegocioException;
import com.sistema.util.jsf.FacesUtil;
import com.sistema.validation.CodigoNumero;

@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Usuarios usuarios;
	@Inject
	private Clientes clientes;
	@Inject
	private Produtos produtos;
	@Inject
	private CadastroPedidoService pedidoService;

	private Pedido pedido;
	private Produto produtoLinhaEditavel;

	private String numero;

	public CadastroPedidoBean() {
		limpar();
	}

	public void init() {
		if (!(FacesUtil.IsPostBack())) {
			this.pedido.adicionarItemVazio();
			calcularValorTotal();
		} else {
			System.out.println("PreRendewView");
		}
	}

	private void limpar() {
		this.pedido = new Pedido();
		this.pedido.setEnderecoEntrega(new EnderecoEntrega());
	}

	public void salvar() {
		
		this.pedido.removerItemVazio();
		
		try {
			this.pedido = this.pedidoService.salvar(this.pedido);
			FacesUtil.addMessage("Pedido salvo com sucesso", FacesMessage.SEVERITY_INFO);

		}catch (NegocioException ne) {
			FacesUtil.addMessage(ne.getMessage(), FacesMessage.SEVERITY_ERROR);
		}finally {
			this.pedido.adicionarItemVazio();
		}

	}

	public FormaPagamento[] getFormaPagamento() {
		return FormaPagamento.values();
	}

	public void calcularValorTotal() {
		if (this.pedido != null)
			this.pedido.calcularValorTotal();
	}

	public List<Usuario> autoCompleteVendedor(String nome) {
		return this.usuarios.porNome(nome);
	}

	public List<Cliente> autoCompleteCliente(String nome) {
		return this.clientes.porNome(nome);
	}

	public boolean isEditando() {
		return pedido.getId() != null;
	}

	public void carregarProdutoLinhaEditavel() {
		if (this.pedido.getItens().size() == 0)
			this.pedido.adicionarItemVazio();

		ItemPedido item = this.pedido.getItens().get(0);

		if (produtoLinhaEditavel != null) {
			if (existeItemNaLista(this.produtoLinhaEditavel)) {
				FacesUtil.addMessage("Esse produto já esta em sua lista", FacesMessage.SEVERITY_ERROR);
			} else {
				item.setProduto(this.produtoLinhaEditavel);
				item.setValorUnitario(this.produtoLinhaEditavel.getValorUnitario());

				this.produtoLinhaEditavel = null;
				this.numero = null;
				this.pedido.adicionarItemVazio();

				this.pedido.calcularValorTotal();
			}

		}
	}

	private boolean existeItemNaLista(Produto produto) {
		boolean existe = false;
		for (ItemPedido item : this.getPedido().getItens()) {
			if (produto.equals(item.getProduto())) {
				existe = true;
				break;
			}
		}
		return existe;
	}

	public void atualizarQuantidade(ItemPedido item, int linha) {
		// se quantidade menor que 1 remove
		if (item.getQuantidade() < 1) {
			// se for a linha 0 não remove
			if (linha == 0) {
				item.setQuantidade(1);
			} else {
				this.pedido.getItens().remove(linha);
			}
		}

		this.pedido.calcularValorTotal();

	}

	public List<Produto> completarProduto(String nome) {
		return this.produtos.porNome(nome);
	}

	public void carregarProdutoPorNumero() {
		if (StringUtils.isNoneEmpty(this.numero)) {
			this.produtoLinhaEditavel = this.produtos.porNumero(this.numero);
			this.carregarProdutoLinhaEditavel();
		}
	}

	// ------------------------------------

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Produto getProdutoLinhaEditavel() {
		return produtoLinhaEditavel;
	}

	public void setProdutoLinhaEditavel(Produto produtoLinhaEditavel) {
		this.produtoLinhaEditavel = produtoLinhaEditavel;
	}

	@CodigoNumero
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

}
