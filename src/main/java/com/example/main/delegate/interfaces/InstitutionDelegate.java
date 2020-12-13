package com.example.main.delegate.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.main.model.Institution;

public interface InstitutionDelegate {
	
	public Institution getInstitution(long id);
	
	public List<Institution> getAllInstitutions();
	
	public void createInstitution(Institution institution);
	
	public void updateInstitution(long id, Institution institution);
	
	public void deleteInstitution(long id);

}
