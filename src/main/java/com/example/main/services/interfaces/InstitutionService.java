package com.example.main.services.interfaces;

import java.util.NoSuchElementException;
import java.util.Optional;

import com.example.main.exceptions.InstitutionWithoutNameException;
import com.example.main.exceptions.URLWithoutProtocolException;
import com.example.main.model.Institution;

public interface InstitutionService {
	
	public void saveInstitution(Institution institution) throws URLWithoutProtocolException, InstitutionWithoutNameException;
	
	public void editInstitution(Institution institution) throws URLWithoutProtocolException, InstitutionWithoutNameException;
		
	public void deleteInstitution(Institution institution);
	
	public Optional<Institution> findById(long id) throws NoSuchElementException;
	
	public Iterable<Institution> findAll();
	
	public long getNumberOfInstitutions();

}
