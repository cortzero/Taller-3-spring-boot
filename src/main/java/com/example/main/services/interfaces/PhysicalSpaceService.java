package com.example.main.services.interfaces;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import com.example.main.exceptions.No5DigitsExternalIDException;
import com.example.main.model.Physicalspace;

public interface PhysicalSpaceService {
	
	public void save(Physicalspace physSp) throws No5DigitsExternalIDException, NoSuchElementException, NumberFormatException;
	
	public void update(Physicalspace physSp) throws No5DigitsExternalIDException, NoSuchElementException, NumberFormatException;
	
	public void delete(Physicalspace physSp);
	
	public Physicalspace findById(long id) throws NoSuchElementException;
	
	public List<Physicalspace> findByName(String name);
	
	public List<Physicalspace> findByExtId(String extId);
	
	public List<Physicalspace> findAll();
	
	public List<Physicalspace> findPhysicalSpacesWithADateRange(Date startDate, Date endDate);

}
