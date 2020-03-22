package com.jeeasy.engine.database.eaos;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;

import com.jeeasy.engine.database.entities.AbstractEntity;

public abstract class AbstractEAO<E extends AbstractEntity> {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Inject
	private Instance<E> entityBuilder;
	
	private Class<E> entityClass;
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void postConstruct() {
		entityClass = (Class<E>) entityBuilder.get().getClass();
	}
	
	public E persist(E entity) {
		entityManager.persist(entity);
		return entity;
	}
	
	public E find(Long id) {
		return entityManager.find(entityClass, id);
	}
	
	public E merge(E entity) {
		return entityManager.merge(entity);
	}
	
	public List<E> findAll() {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT ");
		sb.append(" 	entity ");
		sb.append("	FROM ").append(entityClass.getSimpleName()).append(" entity ");
		
		TypedQuery<E> query = createTypedQuery(sb.toString());
		return getResultList(query);
	}
	
	public void delete(Long id) {
		E toDelete = find(id);
		delete(toDelete);
	}
	
	public void delete(E entity) {
		if (entity != null) {
			entityManager.remove(entity);
		}
	}
	
	public void lock(E entity) {
		entityManager.lock(entity, LockModeType.PESSIMISTIC_WRITE);
	}
	
	public TypedQuery<E> createTypedQuery(String jpql) {
		return createTypedQuery(jpql, entityClass);
	}
	
	public <T> TypedQuery<T> createTypedQuery(String jpql, Class<T> queryClass) {
		return entityManager.createQuery(jpql, queryClass);
	}
	
	public Query createNativeQuery(String sql) {
		return entityManager.createNamedQuery(sql);
	}
	
	public Query createQuery(String jpql) {
		return entityManager.createQuery(jpql);
	}
	
	public <T> T getSingleResult(TypedQuery<T> query) {
		try {
			return query.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}
	
	public <T> Optional<T> getOptionalSingleResult(TypedQuery<T> query) {
		return Optional.ofNullable(getSingleResult(query));
	}
	
	public <T> List<T> getResultList(TypedQuery<T> query) {
		return query.getResultList();
	}
	
	public Session getHibernateSession() {
		return entityManager.unwrap(Session.class);
	}
	
	public void detach(E entity) {
		entityManager.detach(entity);
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public TypedQuery<E> createTypedQuery(StringBuilder sb) {
		return createTypedQuery(sb.toString());
	}
	
	public Query createQuery(StringBuilder sb) {
		return createQuery(sb.toString());
	}
	
	public <T> TypedQuery<T> createTypedQuery(StringBuilder sb, Class<T> queryClass) {
		return createTypedQuery(sb.toString(), queryClass);
	}
}
