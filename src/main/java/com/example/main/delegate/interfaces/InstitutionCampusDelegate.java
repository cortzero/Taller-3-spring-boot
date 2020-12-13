package com.example.main.delegate.interfaces;

import java.util.List;

import com.example.main.model.Institutioncampus;

public interface InstitutionCampusDelegate {
	
	public Institutioncampus getCampus(long id);
	
	public List<Institutioncampus> getAllCampus();
	
	public void createCampus(Institutioncampus campus);
	
	public void updateCampus(long id, Institutioncampus campus);
	
	public void deleteCampus(long id);

}
