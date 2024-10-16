package com.example.main.services.interfaces;

import java.util.List;
import java.util.NoSuchElementException;

import com.example.main.model.HatCapacitydetail;

public interface HatCapacitydetailService {
	
public void save(HatCapacitydetail hatCapacitydetail) throws NoSuchElementException;
	
	public void update(HatCapacitydetail hatCapacitydetail) throws NoSuchElementException;
		
	public void delete(HatCapacitydetail hatCapacitydetail);
	
	public HatCapacitydetail findById(long id);
	
	public List<HatCapacitydetail> findAll();

	public boolean contains(HatCapacitydetail capacity);
	

}
