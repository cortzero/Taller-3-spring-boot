package com.example.main.delegate.interfaces;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.example.main.model.HatCapacitydetail;


public interface HatCapacityDelegate {
	
public HatCapacitydetail getCapacity(long id);
	
	public List<HatCapacitydetail> getAllCapacities();
	
	public HttpStatus createCapacity(HatCapacitydetail capacity);
	
	public void updateCapacity(long id, HatCapacitydetail capacity);
	
	public void deleteCapacity(long id);

}
