package com.sistema.repositoty;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.sistema.model.Usuario;

public class Usuarios implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Usuario porId(Long id) {
		return manager.find(Usuario.class, id);
	}

	public List<Usuario> vendedores() {
		return manager.createQuery("from Usuario", Usuario.class).getResultList();
	}

	public List<Usuario> porNome(String nome) {
		return manager.createQuery("from Usuario where upper(nome) like :nome", Usuario.class)
				.setParameter("nome", nome.toUpperCase()+"%").getResultList();
	}

}
