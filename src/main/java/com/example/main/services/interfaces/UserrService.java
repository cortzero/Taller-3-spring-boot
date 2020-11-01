package com.example.main.services.interfaces;

import java.util.Optional;

import com.example.main.model.Userr;
import com.example.main.model.UserrType;

public interface UserrService {

	public void save(Userr user);

	public Optional<Userr> findById(long id);

	public Iterable<Userr> findAll();

	public Iterable<Userr> findAllAdministrators();

	public Iterable<Userr> findAllOperators();

	public void delete(Userr user);
	
	public UserrType[] getTypes();
}
