package com.example.main.restservice.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.main.model.HatCapacitydetail;


public interface HatCapacityRestService {
	
public ResponseEntity<String> createCapacity(HatCapacitydetail capacity);
	
	public ResponseEntity<String> updateCapacity(long id, HatCapacitydetail capacity);
	
	public ResponseEntity<String> deleteCapacity(long id);
	
	public HatCapacitydetail getCapacity(long id);
	
	public List<HatCapacitydetail> getAllCapacities();

}
