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

import com.example.main.exceptions.PhysicalSpaceTypeWithoutInstitutionException;
import com.example.main.exceptions.PhysicalSpaceTypeWithoutNameException;
import com.example.main.model.Physicalspacetype;
import com.example.main.restservice.interfaces.PhysicalSpaceTypeRestService;
import com.example.main.services.interfaces.PhysicalSpaceTypeService;

@RestController
@RequestMapping("admin/physicalspacetype")
public class PhysicalSpaceTypeRestServiceImpl implements PhysicalSpaceTypeRestService {
	
	@Autowired
	private PhysicalSpaceTypeService service;

	@Override
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createPhysicalSpaceType(Physicalspacetype physicalSpaceType)
			throws PhysicalSpaceTypeWithoutNameException, PhysicalSpaceTypeWithoutInstitutionException,
			NoSuchElementException {
		service.save(physicalSpaceType);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@Override
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updatePhysicalSpaceType(@PathVariable long id, Physicalspacetype physicalSpaceType)
			throws PhysicalSpaceTypeWithoutNameException, PhysicalSpaceTypeWithoutInstitutionException {
		service.update(physicalSpaceType);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@Override
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deletePhysicalSpaceType(@PathVariable long id) {
		service.delete(service.findById(id));
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@Override
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE, MediaType.TEXT_HTML_VALUE})
	public ResponseEntity<Physicalspacetype> getPhysicalSpaceType(@PathVariable long id) {
		return new ResponseEntity<Physicalspacetype>(service.findById(id), HttpStatus.OK);
	}

	@Override
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Physicalspacetype>> getAllPhysicalSpaceTypes() {
		return new ResponseEntity<List<Physicalspacetype>>(service.findAll(), HttpStatus.OK);
	}

}
