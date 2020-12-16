package com.example.main.delegate.interfaces;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.example.main.model.Institutioncampus;

public interface InstitutionCampusDelegate {
	
	public Institutioncampus getCampus(long id);
	
	public List<Institutioncampus> getAllCampus();
	
	public HttpStatus createCampus(Institutioncampus campus);
	
	public void updateCampus(long id, Institutioncampus campus);
	
	public void deleteCampus(long id);

}
