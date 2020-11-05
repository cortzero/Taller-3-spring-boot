package com.example.main.services.interfaces;

import java.util.Optional;

import com.example.main.model.Person;

public interface PersonService {
	
	public void save(Person person);
	
	public void delete(Person person);
	
	public Optional<Person> findById(long id);
	
	public Iterable<Person> findAll();

}
