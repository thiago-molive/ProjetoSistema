package com.sistema.repositoty;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import com.sistema.model.Cliente;
import com.sistema.model.Pedido;
import com.sistema.model.StatusPedido;
import com.sistema.model.Usuario;
import com.sistema.repositoty.filter.PedidoFilter;

public class Pedidos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public List<Pedido> pedidoFiltrados(PedidoFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Pedido> query = builder.createQuery(Pedido.class);
		// Equivalente ao p de 'from pedido p'
		Root<Pedido> from = query.from(Pedido.class);

		Join<Pedido, Cliente> joinCliente = (Join) from.fetch("cliente");
		Join<Pedido, Usuario> joinUsuario = (Join) from.fetch("vendedor");

		Predicate predicate = builder.and();

		if (filtro.getNumeroDe() != null) {
			predicate = builder.and(predicate, builder.ge(from.get("id"), filtro.getNumeroDe()));
		}

		if (filtro.getNumeroAte() != null) {
			predicate = builder.and(predicate, builder.le(from.get("id"), filtro.getNumeroAte()));
		}

		if (filtro.getDataCriacaoDe() != null) {
			predicate = builder.and(predicate,
					builder.greaterThanOrEqualTo(from.<Date>get("dataCriacao"), filtro.getDataCriacaoDe()));
		}

		if (filtro.getDataCriacaoAte() != null) {
			//Correção do bug do lesThanOrEqual
			Calendar chatisse = Calendar.getInstance();
			chatisse.setTime(filtro.getDataCriacaoAte());
			chatisse.add(Calendar.DATE, +1);
			predicate = builder.and(predicate,
					builder.lessThanOrEqualTo(from.get("dataCriacao"),chatisse.getTime()));
		}

		if (StringUtils.isNotBlank(filtro.getCliente())) {
			predicate = builder.and(predicate, builder.like(joinCliente.get("nome"), "%" + filtro.getCliente() + "%"));
		}

		if (StringUtils.isNotBlank(filtro.getVendedor())) {
			predicate = builder.and(predicate, builder.like(joinUsuario.get("nome"), "%" + filtro.getVendedor() + "%"));
		}

		if (filtro.getStatus() != null && filtro.getStatus().length > 0) {
			Expression<StatusPedido> a = from.get("status");
			predicate = builder.and(predicate, a.in(Arrays.asList(filtro.getStatus())));
		}

		TypedQuery<Pedido> tquery = manager
				.createQuery(query.select(from).where(predicate).orderBy(builder.asc(from.get("id"))));
		return tquery.getResultList();
	}
}
