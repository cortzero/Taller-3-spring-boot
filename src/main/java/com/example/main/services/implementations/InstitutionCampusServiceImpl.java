package com.example.main.services.implementations;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.example.main.exceptions.CampusWithNoZeroOccupationException;
import com.example.main.exceptions.CampusWithoutNameException;
import com.example.main.model.Institutioncampus;
import com.example.main.repositories.InstitutionCampusRepository;
import com.example.main.services.interfaces.InstitutionCampusService;
import com.example.main.services.interfaces.InstitutionService;

@Service
@ComponentScan("com.example.main.repositories")
public class InstitutionCampusServiceImpl implements InstitutionCampusService {

	private InstitutionCampusRepository repository;
	private InstitutionService instService;
	
	public InstitutionCampusServiceImpl(InstitutionCampusRepository repository, InstitutionService instService) {
		this.repository = repository;
		this.instService = instService;
	}

	@Override
	public void saveInstitutionCampus(Institutioncampus instCampus) throws CampusWithoutNameException, CampusWithNoZeroOccupationException, NoSuchElementException {
		checkConditions(instCampus);
		repository.save(instCampus);
	}

	@Override
	public void editInstitutionCampus(Institutioncampus instCampus) throws CampusWithoutNameException, CampusWithNoZeroOccupationException, NoSuchElementException {
		checkConditions(instCampus);
		Institutioncampus existingCampus = repository.findById(instCampus.getInstcamId()).get();
		existingCampus.setInstcamName(instCampus.getInstcamName());
		existingCampus.setInstcamOccupation(instCampus.getInstcamOccupation());
		existingCampus.setInstitution(instCampus.getInstitution());
		repository.save(existingCampus);
	}

	@Override
	public Optional<Institutioncampus> getInstitutionCampus(long id) throws NoSuchElementException {
		return repository.findById(id);
	}

	@Override
	public long getNumberOfCampus() {
		return repository.count();
	}
	
	private void checkConditions(Institutioncampus instCampus) throws CampusWithoutNameException, CampusWithNoZeroOccupationException, NoSuchElementException {
		if(instCampus.getInstcamName() == null || instCampus.getInstcamName().isEmpty()) {
			throw new CampusWithoutNameException("No se registró el nombre del campus");
		}
		if(instCampus.getInstcamOccupation().intValue() != 0) {
			throw new CampusWithNoZeroOccupationException("Fomato no válido de la ocupación del campus.");
		}
		if(!instService.findById(instCampus.getInstitution().getInstId()).isPresent()) {
			throw new NoSuchElementException("No existe la institución especificada.");
		}
	}

	@Override
	public void deleteInstitutionCampus(Institutioncampus instCampus) {
		repository.delete(instCampus);
	}

	@Override
	public Iterable<Institutioncampus> findAll() {
		return repository.findAll();
	}
}
