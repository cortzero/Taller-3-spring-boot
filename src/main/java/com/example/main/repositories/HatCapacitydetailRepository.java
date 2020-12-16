package com.example.main.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.main.model.HatCapacitydetail;

@Repository
public interface HatCapacitydetailRepository extends CrudRepository<HatCapacitydetail, Long>  {

}
