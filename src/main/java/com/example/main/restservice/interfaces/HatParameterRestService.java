package com.example.main.restservice.interfaces;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;

import com.example.main.model.HatParameter;


public interface HatParameterRestService {
	
public ResponseEntity<String> createHatParameter(HatParameter hatParameter) throws NoSuchElementException;
	
	public ResponseEntity<String> updateHatParameter(long id, HatParameter hatParameter) throws NoSuchElementException;
	
	public ResponseEntity<String> deleteHatParameter(long id);
	
	public HatParameter getHatParameter(long id);
	
	public List<HatParameter> getAllHatParameters();

}
