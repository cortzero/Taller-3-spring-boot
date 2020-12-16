package com.example.main.restservice.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.main.exceptions.InstitutionWithoutNameException;
import com.example.main.exceptions.URLWithoutProtocolException;
import com.example.main.model.Institution;

public interface InstitutionRestService {
	
	public ResponseEntity<String> createIntitution(Institution institution) throws URLWithoutProtocolException, InstitutionWithoutNameException;
	
	public ResponseEntity<String> updateInstitution(long id, Institution institution) throws URLWithoutProtocolException, InstitutionWithoutNameException;
	
	public ResponseEntity<String> deleteInstitution(long id);
	
	public Institution getInstitution(long id);
	
	public List<Institution> getAllInstitutions();

}
