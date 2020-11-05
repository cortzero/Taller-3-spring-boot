package com.example.main.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.main.model.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
	
}
