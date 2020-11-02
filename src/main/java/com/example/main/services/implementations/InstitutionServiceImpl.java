package com.example.main.services.implementations;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.example.main.exceptions.InstitutionWithoutNameException;
import com.example.main.exceptions.URLWithoutProtocolException;
import com.example.main.model.Institution;
import com.example.main.repositories.InstitutionRepository;
import com.example.main.services.interfaces.InstitutionService;

@Service
@ComponentScan("com.example.main.repositories")
public class InstitutionServiceImpl implements InstitutionService {
	
	private InstitutionRepository repo;
	
	@Autowired
	public InstitutionServiceImpl(InstitutionRepository repo) {
		this.repo = repo;
	}
	
	public void saveInstitution(Institution institution) throws URLWithoutProtocolException, InstitutionWithoutNameException {
		checkConditions(institution);
		repo.save(institution);
	}
  
	public void editInstitution(Institution institution) throws URLWithoutProtocolException, InstitutionWithoutNameException {
		checkConditions(institution);
		Institution existingInstitution = repo.findById(institution.getInstId()).get();
		existingInstitution.setInstName(institution.getInstName());
		existingInstitution.setInstAcademicserverurl(institution.getInstAcademicserverurl());
		repo.save(existingInstitution);
	}
  
	public Optional<Institution> getInstitution(long id) throws NoSuchElementException {
		return repo.findById(id); 
	}
	
	public long getNumberOfInstitutions() {
		return repo.count();
	}
	
	public void checkConditions(Institution institution) throws InstitutionWithoutNameException, URLWithoutProtocolException {
		if(institution.getInstName() == null || institution.getInstName().isEmpty()) {
			throw new InstitutionWithoutNameException("No se especificó el nombre de la institución");
		}
		if(!institution.getInstAcademicserverurl().startsWith("https://")) { 
			throw new URLWithoutProtocolException("URL sin protocolo.");
		}
	}
	
	@Override
	public void deleteInstitution(Institution institution) {
		repo.delete(institution);
	}

	@Override
	public Iterable<Institution> findAll() {
		return repo.findAll();
	}
	
}
