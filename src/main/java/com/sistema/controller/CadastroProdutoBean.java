package com.sistema.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import com.sistema.model.Categoria;
import com.sistema.model.Produto;
import com.sistema.repositoty.Categorias;
import com.sistema.service.CadastroProdutoService;
import com.sistema.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private Categorias categorias; // Repositoty
	@Inject
	private CadastroProdutoService cadastroProdutoService;

	private Produto produto;
	private Categoria categoriaPai; // Categoria

	private List<Categoria> categoriasPai; // Lista de Categorias
	private List<Categoria> subCategorias; // Lista de Sub-Categorias

	public CadastroProdutoBean() {
		limpar();
	}

	private void limpar() {
		produto = new Produto();
		categoriaPai = null;
		subCategorias = new ArrayList<>();
	}
	
	public void carregarSubCategorias() {
		if (this.categoriaPai != null) {
			this.subCategorias = categorias.getSubCategorias(categoriaPai);
		}
	}

	// sendo chamado in-view com metadata preRenderView
	public void init() {
		if (!FacesUtil.IsPostBack()) {
			categoriasPai = categorias.buscarCategoriasPai();

			if (this.categoriaPai != null) {
				carregarSubCategorias();
			}
		}
	}


	public void salvar() {
		this.produto = this.cadastroProdutoService.salvar(this.produto);
		limpar();
		FacesUtil.addMessage("produto salvo com sucesso", FacesMessage.SEVERITY_INFO);
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
		if (produto != null) {
			this.categoriaPai = this.produto.getCategoria().getCategoriaPai();
		}
	}

	public List<Categoria> getCategoriasPai() {
		return categoriasPai;
	}

	@NotNull
	public Categoria getCategoriaPai() {
		return categoriaPai;
	}

	public void setCategoriaPai(Categoria categoriaPai) {
		this.categoriaPai = categoriaPai;
	}

	public List<Categoria> getSubCategorias() {
		return subCategorias;
	}
	
	public boolean isEdit(){
		return this.produto.getId() != null;
	}
}
