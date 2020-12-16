package com.example.main.daos.implementations;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.main.daos.interfaces.InstitutionDAO;
import com.example.main.model.Institution;

@Repository
@Scope("singleton")
public class InstitutionDAOImpl implements InstitutionDAO {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void save(Institution institution) {
		entityManager.persist(institution);
	}

	@Override
	@Transactional
	public void update(Institution institution) {
		entityManager.merge(institution);
	}

	@Override
	@Transactional
	public void delete(Institution institution) {
		entityManager.remove(institution);
	}

	@Override
	@Transactional(readOnly = true)
	public Institution findById(long id) {
		return entityManager.find(Institution.class, id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Institution> findAll() {
		String jpql = "SELECT a FROM Institution a";
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public boolean contains(Institution institution) {
		return entityManager.contains(institution);
	}

}
