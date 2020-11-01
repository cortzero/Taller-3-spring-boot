package com.example.main.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.main.model.Institution;

@Repository
public interface InstitutionRepository extends CrudRepository<Institution, Long> {

}
