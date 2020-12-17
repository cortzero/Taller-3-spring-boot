package com.example.main.services.interfaces;

import java.util.List;
import java.util.NoSuchElementException;

import com.example.main.model.HatParameter;

public interface HatParamaterService {
	
public void save(HatParameter hatParameter) throws NoSuchElementException;
	
	public void update(HatParameter hatParameter) throws NoSuchElementException;
	
	public void delete(HatParameter hatParameter);
	
	public HatParameter findById(long id);
	
	public List<HatParameter> findAll();

	public boolean contains(HatParameter hatParameter);


}
