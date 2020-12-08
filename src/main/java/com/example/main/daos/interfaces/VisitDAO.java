package com.example.main.daos.interfaces;

import java.util.List;

import com.example.main.model.Visit;

public interface VisitDAO {
	
	public void save(Visit visit);
	
	public void update(Visit visit);
	
	public void delete(Visit visit);
	
	public Visit findById(long id);
	
	public List<Visit> findAll();

}
