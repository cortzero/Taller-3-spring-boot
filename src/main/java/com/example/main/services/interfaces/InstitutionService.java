package com.example.main.services.interfaces;

import java.util.List;
import java.util.NoSuchElementException;

import com.example.main.exceptions.InstitutionWithoutNameException;
import com.example.main.exceptions.URLWithoutProtocolException;
import com.example.main.model.Institution;

public interface InstitutionService {
	
	public void save(Institution institution) throws URLWithoutProtocolException, InstitutionWithoutNameException;
	
	public void update(Institution institution) throws URLWithoutProtocolException, InstitutionWithoutNameException;
		
	public void delete(Institution institution);
	
	public Institution findById(long id) throws NoSuchElementException;
	
	public List<Institution> findAll();
	
	public boolean contains(Institution institution);

}
