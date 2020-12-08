package com.example.main.daos.implementations;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.main.daos.interfaces.PhysicalspacetypeDAO;
import com.example.main.model.Physicalspacetype;

@Repository
@Scope("singleton")
public class PhysicalspacetypeDAOImpl implements PhysicalspacetypeDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void save(Physicalspacetype physicalspacetype) {
		entityManager.persist(physicalspacetype);
	}

	@Override
	@Transactional
	public void update(Physicalspacetype physicalspacetype) {
		entityManager.merge(physicalspacetype);
	}

	@Override
	@Transactional
	public void delete(Physicalspacetype physicalspacetype) {
		entityManager.remove(physicalspacetype);
	}

	@Override
	@Transactional(readOnly = true)
	public Physicalspacetype findById(long id) {
		return entityManager.find(Physicalspacetype.class, id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Physicalspacetype> findByName(String name) {
		List<Physicalspacetype> physicalSpaceTypes = entityManager.createQuery("SELECT e FROM Physicalspacetype e WHERE e.physpctypeName = :name")
				.setParameter("name", name)
				.getResultList();
		return physicalSpaceTypes;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Physicalspacetype> findByExtId(String extId) {
		List<Physicalspacetype> physicalSpaceTypes = entityManager.createQuery("SELECT e FROM Physicalspacetype e WHERE e.physpctypeExtid = :extId")
				.setParameter("extId", extId)
				.getResultList();
		return physicalSpaceTypes;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Physicalspacetype> findAll() {
		String jpql = "SELECT a FROM Physicalspacetype a";
		return entityManager.createQuery(jpql).getResultList();
	}

}
