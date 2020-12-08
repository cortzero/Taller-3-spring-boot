package com.example.main.daos.interfaces;

import java.util.List;

import com.example.main.model.Institutioncampus;

public interface InstitutionCampusDAO {

	public void save(Institutioncampus institutioncampus);
	
	public void update(Institutioncampus institutioncampus);
	
	public void delete(Institutioncampus institutioncampus);
	
	public Institutioncampus findById(long id);
	
	public List<Institutioncampus> findAll();
	
}
