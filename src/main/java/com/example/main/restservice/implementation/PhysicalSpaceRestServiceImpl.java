package com.example.main.restservice.implementation;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.main.exceptions.No5DigitsExternalIDException;
import com.example.main.model.Physicalspace;
import com.example.main.restservice.interfaces.PhysicalSpaceRestService;
import com.example.main.services.interfaces.PhysicalSpaceService;

@RestController
@RequestMapping("admin/physicalspacetype")
public class PhysicalSpaceRestServiceImpl implements PhysicalSpaceRestService {
	
	@Autowired
	private PhysicalSpaceService service;

	@Override
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createPhysicalSpace(Physicalspace physicalSpace)
			throws No5DigitsExternalIDException, NoSuchElementException, NumberFormatException {
		service.save(physicalSpace);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@Override
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updatePhysicalSpace(long id, Physicalspace physicalSpace)
			throws No5DigitsExternalIDException, NoSuchElementException, NumberFormatException {
		service.update(physicalSpace);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@Override
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deletePhysicalSpace(long id) {
		service.delete(service.findById(id));
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@Override
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE, MediaType.TEXT_HTML_VALUE})
	public ResponseEntity<Physicalspace> getPhysicalSpace(long id) {
		return new ResponseEntity<Physicalspace>(service.findById(id), HttpStatus.OK);
	}

	@Override
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Physicalspace>> getAllPhysicalSpace() {
		return new ResponseEntity<List<Physicalspace>>(service.findAll(), HttpStatus.OK);
	}

}
