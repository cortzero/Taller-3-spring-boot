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

import com.example.main.model.HatParameter;
import com.example.main.restservice.interfaces.HatParameterRestService;
import com.example.main.services.interfaces.HatParamaterService;

@RestController
@RequestMapping("rest/hatParameters")
public class HatParameterRestServiceImpl implements HatParameterRestService {

	@Autowired
	private HatParamaterService hatParameterService;
	
	
	@Override
	@PostMapping("/create")
	public @ResponseBody ResponseEntity<String> createHatParameter(@RequestBody HatParameter hatParameter) {
		hatParameterService.save(hatParameter);
		int size = hatParameterService.findAll().size();
		if(hatParameterService.findAll().size() == size +1) {
			return new ResponseEntity<String>(HttpStatus.CREATED);
		}
		return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
	}

	@Override
	@PutMapping("/{id}")
	public @ResponseBody ResponseEntity<String> updateHatParameter(@PathVariable("id") long id, @RequestBody HatParameter hatParameter) {
		hatParameterService.update(hatParameter);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@Override
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<String> deleteHatParameter(@PathVariable("id") long id) {
		hatParameterService.delete(hatParameterService.findById(id));
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	public @ResponseBody HatParameter getHatParameter(@PathVariable("id") long id) {
		return hatParameterService.findById(id);
	}

	@Override
	@GetMapping("/")
	public @ResponseBody List<HatParameter> getAllHatParameters() {
		return hatParameterService.findAll();
	}

}
