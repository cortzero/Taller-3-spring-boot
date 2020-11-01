package com.example.main.services.implementations;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.main.model.Userr;
import com.example.main.model.UserrType;
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
	public Optional<Userr> findById(long id) {
		return repository.findById(id);
	}

	@Override
	public Iterable<Userr> findAll() {
		return repository.findAll();
	}

	@Override
	public Iterable<Userr> findAllAdministrators() {
		return repository.findByType(UserrType.administrador);
	}

	@Override
	public Iterable<Userr> findAllOperators() {
		return repository.findByType(UserrType.operador);
	}

	@Override
	public void delete(Userr user) {
		repository.delete(user);
	}

	@Override
	public UserrType[] getTypes() {
		return UserrType.values();
	}

}
