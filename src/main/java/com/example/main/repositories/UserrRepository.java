package com.example.main.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.main.model.Userr;

public interface UserrRepository extends CrudRepository<Userr, Long> {
	
	public Optional<Userr> findByuserName(String userName);
	
}
