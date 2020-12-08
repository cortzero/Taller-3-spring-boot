package com.example.main.daos.implementations;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.main.daos.interfaces.VisitDAO;
import com.example.main.model.Visit;

@Repository
@Scope("singleton")
public class VisitDAOImpl implements VisitDAO {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void save(Visit visit) {
		entityManager.persist(visit);
	}

	@Override
	@Transactional
	public void update(Visit visit) {
		entityManager.merge(visit);
	}

	@Override
	@Transactional
	public void delete(Visit visit) {
		entityManager.remove(visit);
	}

	@Override
	@Transactional(readOnly = true)
	public Visit findById(long id) {
		return entityManager.find(Visit.class, id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Visit> findAll() {
		String jpql = "SELECT a FROM Visit a";
		return entityManager.createQuery(jpql).getResultList();
	}

}
