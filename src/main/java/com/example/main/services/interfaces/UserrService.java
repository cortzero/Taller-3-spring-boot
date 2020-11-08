package com.example.main.services.interfaces;

import java.util.Optional;

import com.example.main.model.Userr;

public interface UserrService {

	public void save(Userr user);
	
	public void delete(Userr user);
	
	public Optional<Userr> findById(long id);
	
	public Iterable<Userr> findAll();
	
	public Iterable<Userr> findAdministrators();
	
	public Iterable<Userr> findOperators();
	
}
