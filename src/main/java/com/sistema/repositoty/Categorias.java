package com.sistema.repositoty;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.sistema.model.Categoria;


public class Categorias implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	EntityManager manager;
	
	public Categoria porId(Long id){
		return manager.find(Categoria.class, id);
	}
	
	public List<Categoria> buscarCategoriasPai(){
		return manager.createQuery("from Categoria p where categoriaPai is null", Categoria.class).getResultList();
	}
	
	public List<Categoria> getSubCategorias(Categoria categoria){
		return manager.createQuery("from Categoria where categoriaPai = :raiz", Categoria.class).setParameter("raiz", categoria).getResultList();
		
	}

}
