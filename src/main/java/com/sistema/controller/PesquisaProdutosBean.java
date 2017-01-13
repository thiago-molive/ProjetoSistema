package com.sistema.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.sistema.model.Produto;
import com.sistema.repositoty.Produtos;
import com.sistema.repositoty.filter.ProdutoFilter;
import com.sistema.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaProdutosBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private Produtos produtos;

	private ProdutoFilter filtro;
	private Produto produtoSelecionado;

	private List<Produto> produtosFiltrados;

	public PesquisaProdutosBean() {
		filtro = new ProdutoFilter();
		produtoSelecionado = new Produto();
	}

	public void excluir() {
		produtos.remover(produtoSelecionado);
		produtosFiltrados.remove(produtoSelecionado);

		FacesUtil.addMessage("Produto " + produtoSelecionado.getNumero() + " - " + produtoSelecionado.getNome()
				+ " removido com sucesso", FacesMessage.SEVERITY_INFO);
	}

	public void pesquisar() {
		produtosFiltrados = produtos.filtrados(filtro);
	}
	
	public void exibeDialog(){
		RequestContext.getCurrentInstance().execute("PF('confirmaExclusao').show()");
	}

	public ProdutoFilter getFiltro() {
		return filtro;
	}

	public List<Produto> getProdutosFiltrados() {
		return produtosFiltrados;
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}

}
