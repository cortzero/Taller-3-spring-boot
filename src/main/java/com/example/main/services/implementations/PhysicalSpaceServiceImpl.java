package com.example.main.services.implementations;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.main.exceptions.No5DigitsExternalIDException;
import com.example.main.model.Physicalspace;
import com.example.main.repositories.PhysicalSpaceRepository;
import com.example.main.services.interfaces.InstitutionCampusService;
import com.example.main.services.interfaces.PhysicalSpaceService;
import com.example.main.services.interfaces.PhysicalSpaceTypeService;

@Service
public class PhysicalSpaceServiceImpl implements PhysicalSpaceService {
	
	private PhysicalSpaceRepository repository;
	private InstitutionCampusService campusService;
	private PhysicalSpaceTypeService physSpTypeService;
	
	@Autowired
	public PhysicalSpaceServiceImpl(PhysicalSpaceRepository repository, InstitutionCampusService campusService, PhysicalSpaceTypeService physSpTypeService) {
		this.repository = repository;
		this.campusService = campusService;
		this.physSpTypeService = physSpTypeService;
	}

	@Override
	public void savePhysicalSpace(Physicalspace physSp) throws No5DigitsExternalIDException, NoSuchElementException, NumberFormatException {
		checkConditions(physSp);
		repository.save(physSp);
	}

	@Override
	public void editPhysicalSpace(Physicalspace physSp) throws No5DigitsExternalIDException, NoSuchElementException, NumberFormatException {
		checkConditions(physSp);
		Physicalspace existing = repository.findById(physSp.getPhyspcId()).get();
		existing.setInstitutioncampus(physSp.getInstitutioncampus());
		existing.setPhysicalspacetype(physSp.getPhysicalspacetype());
		existing.setPhyspcExtid(physSp.getPhyspcExtid());
		repository.save(existing);
	}

	@Override
	public Optional<Physicalspace> findById(long id) throws NoSuchElementException {
		return repository.findById(id);
	}

	@Override
	public long getAmountOfPhysicalSpaces() {
		return repository.count();
	}
	
	private void checkConditions(Physicalspace physSp) throws No5DigitsExternalIDException, NoSuchElementException, NumberFormatException {
		if(physSp.getInstitutioncampus() == null) {
			throw new NoSuchElementException("No se seleccionó un campus.");
		}
		if(physSp.getPhysicalspacetype() == null) {
			throw new NoSuchElementException("No se seleccionó un tipo de espacio físico.");
		}
		if(!campusService.findById(physSp.getInstitutioncampus().getInstcamId()).isPresent()) {
			throw new NoSuchElementException("No existe este campus.");
		}
		if(!physSpTypeService.findById(physSp.getPhysicalspacetype().getPhyspctypeId()).isPresent()) {
			throw new NoSuchElementException("No existe este tipo de espacio físico.");
		}
		if(physSp.getPhyspcExtid().length() < 5 && physSp.getPhyspcExtid().length() > 0 || physSp.getPhyspcExtid().length() < 0 || physSp.getPhyspcExtid().length() > 5 ) {
			throw new No5DigitsExternalIDException("El external id debe tener 5 dígitos.");
		}
		if(!physSp.getPhyspcExtid().isEmpty() && physSp.getPhyspcExtid().length() == 5) {
			Integer.parseInt(physSp.getPhyspcExtid());
		}
	}

	@Override
	public void deletePhysicalSpace(Physicalspace physSp) {
		repository.delete(physSp);
	}

	@Override
	public Iterable<Physicalspace> findAll() {
		return repository.findAll();
	}

}
