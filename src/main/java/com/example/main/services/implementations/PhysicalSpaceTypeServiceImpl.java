package com.example.main.services.implementations;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.example.main.exceptions.PhysicalSpaceTypeWithoutInstitutionException;
import com.example.main.exceptions.PhysicalSpaceTypeWithoutNameException;
import com.example.main.model.Physicalspacetype;
import com.example.main.repositories.PhysicalSpaceTypeRepository;
import com.example.main.services.interfaces.InstitutionService;
import com.example.main.services.interfaces.PhysicalSpaceTypeService;

@Service
@ComponentScan("com.example.main.repositories")
public class PhysicalSpaceTypeServiceImpl implements PhysicalSpaceTypeService {

	private PhysicalSpaceTypeRepository repository;
	private InstitutionService institutionService;
	
	@Autowired
	public PhysicalSpaceTypeServiceImpl(PhysicalSpaceTypeRepository repository, InstitutionService institutionService) {
		this.repository = repository;
		this.institutionService = institutionService;
	}
	
	@Override
	public void savePhysicalSpaceType(Physicalspacetype physicalSpaceType) throws PhysicalSpaceTypeWithoutNameException, PhysicalSpaceTypeWithoutInstitutionException, NoSuchElementException {
		checkConditions(physicalSpaceType);
		repository.save(physicalSpaceType);
	}

	@Override
	public void editPhysicalSpaceType(Physicalspacetype physicalSpaceType) throws PhysicalSpaceTypeWithoutNameException, PhysicalSpaceTypeWithoutInstitutionException, NoSuchElementException {
		checkConditions(physicalSpaceType);
		Physicalspacetype existingPhysST = repository.findById(physicalSpaceType.getPhyspctypeId()).get();
		existingPhysST.setPhyspctypeName(physicalSpaceType.getPhyspctypeName());
		existingPhysST.setInstitution(physicalSpaceType.getInstitution());
		repository.save(existingPhysST);
	}

	@Override
	public Optional<Physicalspacetype> findById(long id) throws NoSuchElementException {
		return repository.findById(id);
	}

	public void checkConditions(Physicalspacetype physicalSpaceType) throws PhysicalSpaceTypeWithoutNameException, PhysicalSpaceTypeWithoutInstitutionException, NoSuchElementException {
		if(physicalSpaceType.getPhyspctypeName() == null || physicalSpaceType.getPhyspctypeName().isEmpty()) {
			throw new PhysicalSpaceTypeWithoutNameException("No se ha asignado un nombre al tipo de espacio físico.");
		}
		if(physicalSpaceType.getInstitution() == null) {
			throw new PhysicalSpaceTypeWithoutInstitutionException("No se ha asignado una institución al tipo de espacio físico.");
		}
		if(!institutionService.findById(physicalSpaceType.getInstitution().getInstId()).isPresent()) {
			throw new NoSuchElementException("No existe esta institución.");
		}
	}

	@Override
	public void deletePhysicalSpaceType(Physicalspacetype physicalSpaceType) {
		repository.delete(physicalSpaceType);
	}

	@Override
	public Iterable<Physicalspacetype> findAll() {
		return repository.findAll();
	}
}
