package com.example.main.restservice.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.main.exceptions.InstitutionWithoutNameException;
import com.example.main.exceptions.URLWithoutProtocolException;
import com.example.main.model.Institution;
import com.example.main.restservice.interfaces.InstitutionRestService;
import com.example.main.services.interfaces.InstitutionService;

@RestController
@RequestMapping("rest/institutions")
public class InstitutionRestServiceImpl implements InstitutionRestService {
	
	@Autowired
	private InstitutionService institutionService;

	@Override
	@PostMapping("/create")
	public @ResponseBody ResponseEntity<String> createIntitution(@RequestBody Institution institution)
			throws URLWithoutProtocolException, InstitutionWithoutNameException {
		institutionService.save(institution);
		if(institutionService.contains(institution)) {
			return new ResponseEntity<String>(HttpStatus.CREATED);
		}
		return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
	}

	@Override
	@PutMapping("/{id}")
	public @ResponseBody ResponseEntity<String> updateInstitution(@PathVariable("id") long id, @RequestBody Institution institution)
			throws URLWithoutProtocolException, InstitutionWithoutNameException {		
		institutionService.update(institution);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@Override
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<String> deleteInstitution(@PathVariable("id") long id) {
		institutionService.delete(institutionService.findById(id));
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	public @ResponseBody Institution getInstitution(@PathVariable("id") long id) {
		return institutionService.findById(id);
	}

	@Override
	@GetMapping("/")
	public @ResponseBody List<Institution> getAllInstitutions() {
		return institutionService.findAll();
	}
}
