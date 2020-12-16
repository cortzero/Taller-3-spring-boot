package com.example.main.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.main.model.HatParameter;

@Repository
public interface HatParameterRepository extends CrudRepository<HatParameter, Long> {

}
