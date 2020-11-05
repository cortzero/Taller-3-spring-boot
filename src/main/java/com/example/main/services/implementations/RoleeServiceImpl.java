package com.example.main.services.implementations;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.main.model.Rolee;
import com.example.main.repositories.RoleeRepository;
import com.example.main.services.interfaces.RoleeService;

public class RoleeServiceImpl implements RoleeService {
	
	private RoleeRepository repository;
	
	@Autowired
	public RoleeServiceImpl(RoleeRepository repository) {
		this.repository = repository;
	}

	@Override
	public void save(Rolee role) {
		repository.save(role);
	}

	@Override
	public void delete(Rolee role) {
		repository.delete(role);
	}

	@Override
	public Optional<Rolee> findById(long id) {
		return repository.findById(id);
	}

	@Override
	public Iterable<Rolee> findAll() {
		return repository.findAll();
	}

}
