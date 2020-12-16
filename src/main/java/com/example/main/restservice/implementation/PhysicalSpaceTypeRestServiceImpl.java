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

import com.example.main.exceptions.PhysicalSpaceTypeWithoutInstitutionException;
import com.example.main.exceptions.PhysicalSpaceTypeWithoutNameException;
import com.example.main.model.Physicalspacetype;
import com.example.main.restservice.interfaces.PhysicalSpaceTypeRestService;
import com.example.main.services.interfaces.PhysicalSpaceTypeService;

@RestController
@RequestMapping("rest/physicalspacetype")
public class PhysicalSpaceTypeRestServiceImpl implements PhysicalSpaceTypeRestService {
	
	@Autowired
	private PhysicalSpaceTypeService service;

	@Override
	@PostMapping("/create")
	public @ResponseBody ResponseEntity<String> createPhysicalSpaceType(@RequestBody Physicalspacetype physicalSpaceType)
			throws PhysicalSpaceTypeWithoutNameException, PhysicalSpaceTypeWithoutInstitutionException,
			NoSuchElementException {
		int size = service.findAll().size();
		service.save(physicalSpaceType);
		if(service.findAll().size() == size + 1) {
			return new ResponseEntity<String>(HttpStatus.CREATED);
		}
		return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
	}

	@Override
	@PutMapping("/{id}")
	public @ResponseBody ResponseEntity<String> updatePhysicalSpaceType(@PathVariable long id, @RequestBody Physicalspacetype physicalSpaceType)
			throws PhysicalSpaceTypeWithoutNameException, PhysicalSpaceTypeWithoutInstitutionException {
		service.update(physicalSpaceType);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@Override
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<String> deletePhysicalSpaceType(@PathVariable("id") long id) {
		service.delete(service.findById(id));
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	public @ResponseBody Physicalspacetype getPhysicalSpaceType(@PathVariable("id") long id) {
		return service.findById(id);
	}

	@Override
	@GetMapping("/")
	public @ResponseBody List<Physicalspacetype> getAllPhysicalSpaceTypes() {
		return service.findAll();
	}

	@Override
	@GetMapping("/find_by_name/{name}")
	public List<Physicalspacetype> findByName(@PathVariable("name") String name) {
		return service.findByName(name);
	}

	@Override
	@GetMapping("/find_by_extid/{extId}")
	public List<Physicalspacetype> findByExtId(@PathVariable("extId") String extId) {
		return service.findByExtId(extId);
	}

}
