package com.example.main.restservice.implementation;

import java.util.List;
import java.util.NoSuchElementException;

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

import com.example.main.exceptions.CampusWithNoZeroOccupationException;
import com.example.main.exceptions.CampusWithoutNameException;
import com.example.main.model.Institutioncampus;
import com.example.main.restservice.interfaces.InstitutionCampusRestService;
import com.example.main.services.interfaces.InstitutionCampusService;

@RestController
@RequestMapping("rest/campus")
public class InstitutionCampusRestServiceImpl implements InstitutionCampusRestService {
	
	@Autowired
	private InstitutionCampusService campusService;
	
	@Override
	@PostMapping("/create")
	public @ResponseBody ResponseEntity<String> createCampus(@RequestBody Institutioncampus campus)
			throws CampusWithoutNameException, CampusWithNoZeroOccupationException, NoSuchElementException {
		int size = campusService.findAll().size();
		campusService.save(campus);
		if(campusService.contains(campus)) {
			return new ResponseEntity<String>(HttpStatus.CREATED);
		}
		return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
	}

	@Override
	@PutMapping("/{id}")
	public @ResponseBody ResponseEntity<String> updateCampus(@PathVariable("id") long id, @RequestBody Institutioncampus campus)
			throws CampusWithoutNameException, CampusWithNoZeroOccupationException, NoSuchElementException {		
		campusService.update(campus);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@Override
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<String> deleteCampus(@PathVariable("id") long id) {
		campusService.delete(campusService.findById(id));
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	public @ResponseBody Institutioncampus getCampus(@PathVariable("id") long id) {
		return campusService.findById(id);
	}

	@Override
	@GetMapping("/")
	public @ResponseBody List<Institutioncampus> getAllCampus() {
		return campusService.findAll();
	}
}
