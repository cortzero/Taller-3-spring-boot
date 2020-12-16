package com.example.main.restservice.implementation;

import java.util.Date;
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

import com.example.main.model.Communityinstance;
import com.example.main.restservice.interfaces.CommunityInstanceRestService;
import com.example.main.services.interfaces.CommunityinstanceService;

@RestController
@RequestMapping("rest/communities")
public class CommunityInstanceRestServiceImpl implements CommunityInstanceRestService {
	
	@Autowired
	private CommunityinstanceService commService;

	@Override
	@PostMapping("/create")
	public @ResponseBody ResponseEntity<String> createCommunity(@RequestBody Communityinstance community) {
		commService.save(community);
		if(commService.contains(community)) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}

	@Override
	@PutMapping("/{id}")
	public @ResponseBody ResponseEntity<String> updateCommunity(@PathVariable("id") long id, @RequestBody Communityinstance community) {
		commService.update(community);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<String> deleteCommunity(@PathVariable("id") long id) {
		commService.delete(commService.findById(id));
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	public @ResponseBody Communityinstance getCommunity(@PathVariable("id") long id) {
		return commService.findById(id);
	}

	@Override
	@GetMapping("/")
	public @ResponseBody List<Communityinstance> getAllCommunities() {
		return commService.findAll();
	}

	@Override
	@GetMapping("/startDate={sDay}?{sMonth}?{sYear}&endDate={eDay}?{eMonth}?{eYear}")
	public @ResponseBody List<Communityinstance> findByDateRange(@PathVariable("sDay") int sDay, 
			@PathVariable("sMonth") int sMonth, 
			@PathVariable("sYear") int sYear, 
			@PathVariable("eDay") int eDay, 
			@PathVariable("eMonth") int eMonth, 
			@PathVariable("eYear") int eYear) {
		Date startDate = new Date(sYear, sMonth, sDay);
		Date endDate = new Date(eYear, eMonth, eDay);
		return commService.findByDateRange(startDate, endDate);
	}
}
