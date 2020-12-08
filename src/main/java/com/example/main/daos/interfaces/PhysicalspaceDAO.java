package com.example.main.daos.interfaces;

import java.util.Date;
import java.util.List;

import com.example.main.model.Physicalspace;

public interface PhysicalspaceDAO {

	public void save(Physicalspace physicalspace);
	
	public void update(Physicalspace physicalspace);
	
	public void delete(Physicalspace physicalspace);
	
	public Physicalspace findById(long id);
	
	public List<Physicalspace> findByName(String name);
	
	public List<Physicalspace> findByExtId(String extId);
	
	public List<Physicalspace> findPhysicalSpacesBetweenDates(Date startDate, Date endDate);
	
	public List<Physicalspace> findByAvailableDate(Date availableDate, Date startDate, Date endDate);
	
	public List<Physicalspace> findAll();
	
	public void clear();
	
}
