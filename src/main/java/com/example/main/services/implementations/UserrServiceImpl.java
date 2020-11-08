package com.example.main.services.implementations;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.main.model.Userr;
import com.example.main.repositories.UserrRepository;
import com.example.main.services.interfaces.UserrService;

@Service
public class UserrServiceImpl implements UserrService {

	private UserrRepository repository;
	
	@Autowired
	public UserrServiceImpl(UserrRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public void save(Userr user) {
		repository.save(user);
	}

	@Override
	public void delete(Userr user) {
		repository.delete(user);
	}

	@Override
	public Optional<Userr> findById(long id) {
		return repository.findById(id);
	}

	@Override
	public Iterable<Userr> findAll() {
		return repository.findAll();
	}

	@Override
	public Iterable<Userr> findAdministrators() {
		return null;
	}

	@Override
	public Iterable<Userr> findOperators() {
		return null;
	}
}
