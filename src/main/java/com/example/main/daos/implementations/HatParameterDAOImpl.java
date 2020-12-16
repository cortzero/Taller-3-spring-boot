package com.example.main.daos.implementations;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.example.main.daos.interfaces.HatParameterDAO;
import com.example.main.model.HatParameter;
import com.example.main.model.Institution;

@Repository
@Scope("singleton")
public class HatParameterDAOImpl implements HatParameterDAO {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void save(HatParameter hatParameter) {
		entityManager.persist(hatParameter);
		
	}

	@Override
	@Transactional
	public void update(HatParameter hatParameter) {
		entityManager.merge(hatParameter);
		
	}

	@Override
	@Transactional
	public void delete(HatParameter hatParameter) {
		entityManager.remove(hatParameter);
	}

	@Override
	public HatParameter findById(long id) {
		return entityManager.find(HatParameter.class, id);
	}

	@Override
	public List<HatParameter> findAll() {
		String jpql = "SELECT h FROM HatParameter h";
		return entityManager.createQuery(jpql).getResultList();
	}
	
	@Override
	public boolean contains(HatParameter hatParameter) {
		return entityManager.contains(hatParameter);
	}

}
