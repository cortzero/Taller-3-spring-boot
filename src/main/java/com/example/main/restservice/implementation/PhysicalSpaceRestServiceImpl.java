package com.example.main.restservice.implementation;

import java.util.Date;
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

import com.example.main.exceptions.No5DigitsExternalIDException;
import com.example.main.model.Physicalspace;
import com.example.main.restservice.interfaces.PhysicalSpaceRestService;
import com.example.main.services.interfaces.PhysicalSpaceService;

@RestController
@RequestMapping("rest/physicalspace")
public class PhysicalSpaceRestServiceImpl implements PhysicalSpaceRestService {
	
	@Autowired
	private PhysicalSpaceService service;

	@Override
	@PostMapping("/create")
	public @ResponseBody ResponseEntity<String> createPhysicalSpace(@RequestBody Physicalspace physicalSpace)
			throws No5DigitsExternalIDException, NoSuchElementException, NumberFormatException {
		int size = service.findAll().size();
		service.save(physicalSpace);
		if(service.findAll().size() == size + 1) {
			return new ResponseEntity<String>(HttpStatus.CREATED);
		}
		return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
	}

	@Override
	@PutMapping("/{id}")
	public @ResponseBody ResponseEntity<String> updatePhysicalSpace(@PathVariable("id") long id, @RequestBody Physicalspace physicalSpace)
			throws No5DigitsExternalIDException, NoSuchElementException, NumberFormatException {
		service.update(physicalSpace);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@Override
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<String> deletePhysicalSpace(@PathVariable("id") long id) {
		service.delete(service.findById(id));
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	public @ResponseBody Physicalspace getPhysicalSpace(@PathVariable("id") long id) {
		return service.findById(id);
	}

	@Override
	@GetMapping("/")
	public @ResponseBody List<Physicalspace> getAllPhysicalSpace() {
		return service.findAll();
	}

	@Override
	@GetMapping("/find_by_name/{name}")
	public @ResponseBody List<Physicalspace> findByName(@PathVariable("name") String name) {
		return service.findByName(name);
	}

	@Override
	@GetMapping("/find_by_extid/{extId}")
	public @ResponseBody List<Physicalspace> findByExtId(@PathVariable("extId") String extId) {
		return service.findByExtId(extId);
	}

	@Override
	@GetMapping("/startDate={sDay}?{sMonth}?{sYear}&endDate={eDay}?{eMonth}?{eYear}")
	public @ResponseBody List<Physicalspace> findPhysicalSpacesWithADateRange(@PathVariable("sDay") int sDay, 
			@PathVariable("sMonth") int sMonth, 
			@PathVariable("sYear") int sYear, 
			@PathVariable("eDay") int eDay, 
			@PathVariable("eMonth") int eMonth, 
			@PathVariable("eYear") int eYear) {
		Date startDate = new Date(sYear, sMonth, sDay);
		Date endDate = new Date(eYear, eMonth, eDay);
		return service.findPhysicalSpacesWithADateRange(startDate, endDate);
	}

}
