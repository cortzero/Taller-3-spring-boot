package com.example.main.daos.interfaces;

import java.util.List;

import com.example.main.model.Institution;

public interface InstitutionDAO {

	public void save(Institution institution);
	
	public void update(Institution institution);
	
	public void delete(Institution institution);
	
	public Institution findById(long id);
	
	public List<Institution> findAll();
	
	public boolean contains(Institution institution);
	
}
