package com.example.main.restservice.implementation;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.main.exceptions.CampusWithNoZeroOccupationException;
import com.example.main.exceptions.CampusWithoutNameException;
import com.example.main.model.Institutioncampus;
import com.example.main.restservice.interfaces.InstitutionCampusRestService;
import com.example.main.services.interfaces.InstitutionCampusService;

@RestController
@RequestMapping("admin/campus")
public class InstitutionCampusRestServiceImpl implements InstitutionCampusRestService {
	
	@Autowired
	private InstitutionCampusService campusService;
	
	@Override
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createCampus(Institutioncampus campus)
			throws CampusWithoutNameException, CampusWithNoZeroOccupationException, NoSuchElementException {
		campusService.save(campus);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@Override
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateCampus(@PathVariable long id, Institutioncampus campus)
			throws CampusWithoutNameException, CampusWithNoZeroOccupationException, NoSuchElementException {		
		campusService.update(campus);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@Override
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteCampus(@PathVariable long id) {
		campusService.delete(campusService.findById(id));
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@Override
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE, MediaType.TEXT_HTML_VALUE})
	public ResponseEntity<Institutioncampus> getCampus(@PathVariable long id) {
		return new ResponseEntity<Institutioncampus>(campusService.findById(id), HttpStatus.OK);
	}

	@Override
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Institutioncampus>> getAllCampus() {
		return new ResponseEntity<List<Institutioncampus>>(campusService.findAll(), HttpStatus.OK);
	}

}
