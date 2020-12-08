package com.example.main.services.implementations;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.main.daos.interfaces.PhysicalspacetypeDAO;
import com.example.main.exceptions.PhysicalSpaceTypeWithoutInstitutionException;
import com.example.main.exceptions.PhysicalSpaceTypeWithoutNameException;
import com.example.main.model.Physicalspacetype;
import com.example.main.services.interfaces.InstitutionService;
import com.example.main.services.interfaces.PhysicalSpaceTypeService;

@Service
@ComponentScan("com.example.main.daos.implementations")
public class PhysicalSpaceTypeServiceImpl implements PhysicalSpaceTypeService {

	private PhysicalspacetypeDAO physicalSpaceTypeDAO;
	private InstitutionService institutionService;
	
	@Autowired
	public PhysicalSpaceTypeServiceImpl(PhysicalspacetypeDAO physicalSpaceTypeDAO, InstitutionService institutionService) {
		this.physicalSpaceTypeDAO = physicalSpaceTypeDAO;
		this.institutionService = institutionService;
	}
	
	@Override
	@Transactional
	public void save(Physicalspacetype physicalSpaceType) throws PhysicalSpaceTypeWithoutNameException, PhysicalSpaceTypeWithoutInstitutionException, NoSuchElementException {
		checkConditions(physicalSpaceType);
		physicalSpaceTypeDAO.save(physicalSpaceType);
	}

	@Override
	@Transactional
	public void update(Physicalspacetype physicalSpaceType) throws PhysicalSpaceTypeWithoutNameException, PhysicalSpaceTypeWithoutInstitutionException, NoSuchElementException {
		checkConditions(physicalSpaceType);
		physicalSpaceTypeDAO.update(physicalSpaceType);
	}

	@Override
	@Transactional
	public void delete(Physicalspacetype physicalSpaceType) {
		physicalSpaceTypeDAO.delete(physicalSpaceType);
	}

	@Override
	@Transactional(readOnly = true)
	public Physicalspacetype findById(long id) throws NoSuchElementException {
		return physicalSpaceTypeDAO.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Physicalspacetype> findByName(String name) {
		return physicalSpaceTypeDAO.findByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Physicalspacetype> findByExtId(String extId) {
		return physicalSpaceTypeDAO.findByExtId(extId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Physicalspacetype> findAll() {
		return physicalSpaceTypeDAO.findAll();
	}
	
	public void checkConditions(Physicalspacetype physicalSpaceType) throws PhysicalSpaceTypeWithoutNameException, PhysicalSpaceTypeWithoutInstitutionException, NoSuchElementException {
		if(physicalSpaceType.getPhyspctypeName() == null || physicalSpaceType.getPhyspctypeName().isEmpty()) {
			throw new PhysicalSpaceTypeWithoutNameException("No se ha asignado un nombre al tipo de espacio físico.");
		}
		if(physicalSpaceType.getInstitution() == null) {
			throw new PhysicalSpaceTypeWithoutInstitutionException("No se ha asignado una institución al tipo de espacio físico.");
		}
		if(institutionService.findById(physicalSpaceType.getInstitution().getInstId()) == null) {
			throw new NoSuchElementException("No existe esta institución.");
		}
	}

}
