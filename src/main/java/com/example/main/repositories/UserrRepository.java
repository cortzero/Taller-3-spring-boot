package com.example.main.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.main.model.Userr;
import com.example.main.model.UserrType;

public interface UserrRepository extends CrudRepository<Userr, Long> {
	
	public List<Userr> findByType(UserrType type);
	
	public Userr findByUserName(String userName);
	
}
