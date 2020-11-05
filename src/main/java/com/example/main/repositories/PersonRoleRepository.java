package com.example.main.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.main.model.PersonRole;
import com.example.main.model.PersonRolePK;

public interface PersonRoleRepository extends CrudRepository<PersonRole, PersonRolePK> {

}
