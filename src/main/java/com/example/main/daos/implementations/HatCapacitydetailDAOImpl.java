package com.example.main.daos.implementations;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.example.main.daos.interfaces.HatCapacitydetailDAO;
import com.example.main.model.HatCapacitydetail;

@Repository
@Scope("singleton")
public class HatCapacitydetailDAOImpl implements HatCapacitydetailDAO{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void save(HatCapacitydetail hatCapacitydetail) {
		
		entityManager.persist(hatCapacitydetail);
		
	}

	@Override
	public void update(HatCapacitydetail hatCapacitydetail) {
		
		entityManager.merge(hatCapacitydetail);
		
	}

	@Override
	public void delete(HatCapacitydetail hatCapacitydetail) {
		
		entityManager.remove(hatCapacitydetail);
		
	}

	@Override
	public HatCapacitydetail findById(long id) {

		return entityManager.find(HatCapacitydetail.class, id);
	}

	@Override
	public List<HatCapacitydetail> findAll() {
		String jpql = "SELECT h FROM HatCapacitydetail h";
		return entityManager.createQuery(jpql).getResultList();
	}
	
	

}
