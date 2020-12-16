package com.example.main.daos.implementations;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.example.main.daos.interfaces.PhysicalspaceDAO;
import com.example.main.model.Physicalspace;

@Repository
@Scope("singleton")
public class PhysicalspaceDAOImpl implements PhysicalspaceDAO {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void save(Physicalspace physicalspace) {
		entityManager.persist(physicalspace);
	}

	@Override
	@Transactional
	public void update(Physicalspace physicalspace) {
		entityManager.merge(physicalspace);
	}

	@Override
	@Transactional
	public void delete(Physicalspace physicalspace) {
		entityManager.remove(physicalspace);
	}

	@Override
	public Physicalspace findById(long id) {
		return entityManager.find(Physicalspace.class, id);
	}

	@Override
	public List<Physicalspace> findByName(String name) {
		List<Physicalspace> physicalSpaces = entityManager.createQuery("SELECT e FROM Physicalspace e WHERE e.physpcName = :name")
				.setParameter("name", name)
				.getResultList();
		return physicalSpaces;
	}

	@Override
	public List<Physicalspace> findByExtId(String extId) {
		List<Physicalspace> physicalSpaces = entityManager.createQuery("SELECT e FROM Physicalspace e WHERE e.physpcExtid = :extId")
				.setParameter("extId", extId)
				.getResultList();
		return physicalSpaces;
	}

	@Override
	public List<Physicalspace> findPhysicalSpacesBetweenDates(Date startDate, Date endDate) {
		List<Physicalspace> physicalSpaces = entityManager.createQuery("SELECT e FROM Physicalspace e INNER JOIN e.communityinstances com "
				+ "WHERE (com.comminstStartdatehour BETWEEN :startDate AND :endDate) AND (com.comminstEnddatehour BETWEEN :startDate AND :endDate)")
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.getResultList();
		return physicalSpaces;
	}

	@Override
	public List<Physicalspace> findByAvailableDate(Date availableDate, Date startDate, Date endDate) {
//		List<Physicalspace> physicalSpaces = entityManager.createQuery("SELECT e FROM Physicalspace e, e.institutioncampus camp INNER JOIN camp.visits v, e INNER JOIN e.communityinstances com "
//				+ "WHERE (:availableDate BETWEEN com.comminstStartdatehour AND com.comminstEnddatehour) AND SIZE(camp.visits) < 10 AND (v.visitEntrancedate BETWEEN :startDate AND :endDate) "
//				+ "AND (v.visitExitdate BETWEEN :startDate AND :endDate)")
		List<Physicalspace> physicalSpaces = entityManager.createQuery("SELECT e FROM Physicalspace e INNER JOIN e.institutioncampus camp "
				+ "WHERE SIZE(camp.visits) < 10 AND SELECT v FROM Visit INNER JOIN camp.visits v WHERE (v.visitEntrancedate BETWEEN :startDate AND :endDate) "
				+ "AND (v.visitExitdate BETWEEN :startDate AND :endDate) AND SELECT com FROM Communityinstance INNER JOIN e.communityinstances com "
				+ "WHERE :availableDate BETWEEN com.comminstStartdatehour AND com.comminstEnddatehour")
				.setParameter("availableDate", availableDate)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.getResultList();
		return physicalSpaces;
	}
	
	@Override
	public List<Physicalspace> findAll() {
		String jpql = "SELECT a FROM Physicalspace a";
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public boolean contains(Physicalspace physicalspace) {
		return entityManager.contains(physicalspace);
	}
}
