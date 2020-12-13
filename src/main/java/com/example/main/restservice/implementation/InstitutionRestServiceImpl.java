package com.example.main.restservice.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.main.exceptions.InstitutionWithoutNameException;
import com.example.main.exceptions.URLWithoutProtocolException;
import com.example.main.model.Institution;
import com.example.main.restservice.interfaces.InstitutionRestService;
import com.example.main.services.interfaces.InstitutionService;

@RestController
@RequestMapping("admin/institutions")
public class InstitutionRestServiceImpl implements InstitutionRestService {
	
	@Autowired
	private InstitutionService institutionService;

	@Override
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createIntitution(Institution institution)
			throws URLWithoutProtocolException, InstitutionWithoutNameException {
		institutionService.save(institution);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@Override
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateInstitution(@PathVariable long id, Institution institution)
			throws URLWithoutProtocolException, InstitutionWithoutNameException {		
		institutionService.update(institution);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@Override
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteInstitution(@PathVariable long id) {
		institutionService.delete(institutionService.findById(id));
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@Override
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE, MediaType.TEXT_HTML_VALUE})
	public ResponseEntity<Institution> getInstitution(@PathVariable long id) {
		return new ResponseEntity<Institution>(institutionService.findById(id), HttpStatus.OK);
	}

	@Override
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Institution>> getAllInstitutions() {
		return new ResponseEntity<List<Institution>>(institutionService.findAll(), HttpStatus.OK);
	}
}
