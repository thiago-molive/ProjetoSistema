package com.sistema.repositoty;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import com.sistema.model.Produto;
import com.sistema.repositoty.filter.ProdutoFilter;

public class Produtos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager manager;

	public List<Produto> filtrados(ProdutoFilter filtro) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
		Root<Produto> from = query.from(Produto.class);

		Predicate predicate = builder.and();

		if (StringUtils.isNotBlank(filtro.getNumero())) {
			predicate = builder.and(predicate, builder.equal(from.get("numero"), filtro.getNumero()));
		}

		if (StringUtils.isNotBlank(filtro.getNome())) {
			predicate = builder.and(predicate, builder.like(from.get("nome"), "%" + filtro.getNome() + "%"));
		}
		TypedQuery<Produto> tq = manager
				.createQuery(query.select(from).where(predicate).orderBy(builder.asc(from.get("nome"))));
		return tq.getResultList();
	}

	public Produto salvar(Produto produto) {
		return manager.merge(produto);
	}

	public Produto porNumero(String numero) {
		try {
			return manager.createQuery("from Produto where upper(numero) = :numero", Produto.class)
					.setParameter("numero", numero.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Produto porId(Long id) {
		return manager.find(Produto.class, id);
	}

}
