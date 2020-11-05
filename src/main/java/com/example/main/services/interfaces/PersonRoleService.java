package com.example.main.services.interfaces;

import java.util.Optional;

import com.example.main.model.PersonRole;
import com.example.main.model.PersonRolePK;

public interface PersonRoleService {

	public void save(PersonRole personRole);
	
	public void delete(PersonRole personRole);
	
	public Optional<PersonRole> findById(PersonRolePK personRolePK);
	
	public Iterable<PersonRole> findAll();
	
}
