package com.example.main.daos.implementations;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.example.main.daos.interfaces.InstitutionCampusDAO;
import com.example.main.model.Institutioncampus;

@Repository
@Scope("singleton")
public class InstitutionCampusDAOImpl implements InstitutionCampusDAO {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void save(Institutioncampus institutioncampus) {
		entityManager.persist(institutioncampus);
	}

	@Override
	@Transactional
	public void update(Institutioncampus institutioncampus) {
		entityManager.merge(institutioncampus);
	}

	@Override
	@Transactional
	public void delete(Institutioncampus institutioncampus) {
		entityManager.remove(institutioncampus);
	}

	@Override
	public Institutioncampus findById(long id) {
		return entityManager.find(Institutioncampus.class, id);
	}

	@Override
	public List<Institutioncampus> findAll() {
		String jpql = "SELECT a FROM Institutioncampus a";
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public boolean contains(Institutioncampus institutioncampus) {
		return entityManager.contains(institutioncampus);
	}

}
