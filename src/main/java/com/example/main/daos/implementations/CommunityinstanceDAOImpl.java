package com.example.main.daos.implementations;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.example.main.daos.interfaces.CommunityinstanceDAO;
import com.example.main.model.Communityinstance;

@Repository
@Scope("singleton")
public class CommunityinstanceDAOImpl implements CommunityinstanceDAO {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void save(Communityinstance communityinstance) {
		entityManager.persist(communityinstance);
	}

	@Override
	@Transactional
	public void update(Communityinstance communityinstance) {
		entityManager.merge(communityinstance);
	}

	@Override
	@Transactional
	public void delete(Communityinstance communityinstance) {
		entityManager.remove(communityinstance);
	}

	@Override
	public Communityinstance findById(long id) {
		return entityManager.find(Communityinstance.class, id);
	}

	@Override
	public List<Communityinstance> findByDateRange(Date startDate, Date endDate) {
		List<Communityinstance> communities = entityManager.createQuery(
				"SELECT e FROM Communityinstance e WHERE (e.comminstStartdatehour BETWEEN :startDate AND :endDate) "
				+ "AND (e.comminstEnddatehour BETWEEN :startDate AND :endDate)")
		.setParameter("startDate", startDate, TemporalType.DATE)
	  	.setParameter("endDate", endDate, TemporalType.DATE)
	  	.getResultList();
		return communities;
	}

	@Override
	public List<Communityinstance> findAll() {
		String jpql = "SELECT a FROM Communityinstance a";
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public boolean contains(Communityinstance communityinstance) {
		return entityManager.contains(communityinstance);
	}

}
