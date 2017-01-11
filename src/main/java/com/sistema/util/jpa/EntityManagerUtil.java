package com.sistema.util.jpa;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class EntityManagerUtil {
	EntityManagerFactory factory;
	
	public EntityManagerUtil() {
		this.factory = Persistence.createEntityManagerFactory("PedidoPU");
	}
	
	@Produces @RequestScoped
	public EntityManager getEntityManager(){
		return factory.createEntityManager();
	}
	
	public void closeEntityManager(@Disposes EntityManager manager){
		manager.close();
	}
}
