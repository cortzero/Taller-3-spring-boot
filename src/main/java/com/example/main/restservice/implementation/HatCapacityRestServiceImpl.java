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

import com.example.main.model.HatCapacitydetail;
import com.example.main.restservice.interfaces.HatCapacityRestService;
import com.example.main.services.interfaces.HatCapacitydetailService;

@RestController
@RequestMapping("rest/campusCapacities")
public class HatCapacityRestServiceImpl implements HatCapacityRestService{
	
	@Autowired
	private HatCapacitydetailService capacityService;

	@Override
	@PostMapping("/create")
	public ResponseEntity<String> createCapacity(@RequestBody HatCapacitydetail capacity) {
		capacityService.save(capacity);
		if(capacityService.contains(capacity)) {
			return new ResponseEntity<String>(HttpStatus.CREATED);
		}
		return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
	}

	@Override
	@PutMapping("/{id}")
	public ResponseEntity<String> updateCapacity(@PathVariable("id") long id, @RequestBody HatCapacitydetail capacity) {
		capacityService.update(capacity);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCapacity(@PathVariable("id") long id) {
		capacityService.delete(capacityService.findById(id));
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	public @ResponseBody HatCapacitydetail getCapacity(@PathVariable("id") long id) {
		return capacityService.findById(id);
	}

	@Override
	@GetMapping("/")
	public @ResponseBody List<HatCapacitydetail> getAllCapacities() {
		return capacityService.findAll();
	}

}
