package com.example.main.services.implementations;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.main.model.PersonRole;
import com.example.main.model.PersonRolePK;
import com.example.main.repositories.PersonRoleRepository;
import com.example.main.services.interfaces.PersonRoleService;

public class PersonRoleServiceImpl implements PersonRoleService {
	
	private PersonRoleRepository repository;
	
	@Autowired
	public PersonRoleServiceImpl(PersonRoleRepository repository) {
		this.repository = repository;
	}

	@Override
	public void save(PersonRole personRole) {
		repository.save(personRole);
	}

	@Override
	public void delete(PersonRole personRole) {
		repository.delete(personRole);
	}

	@Override
	public Optional<PersonRole> findById(PersonRolePK personRolePK) {
		return repository.findById(personRolePK);
	}

	@Override
	public Iterable<PersonRole> findAll() {
		return repository.findAll();
	}

}
