package com.example.main.services.interfaces;

import java.util.Optional;

import com.example.main.model.Rolee;

public interface RoleeService {
	
	public void save(Rolee role);
	
	public void delete(Rolee role);
	
	public Optional<Rolee> findById(long id);
	
	public Iterable<Rolee> findAll();

}
