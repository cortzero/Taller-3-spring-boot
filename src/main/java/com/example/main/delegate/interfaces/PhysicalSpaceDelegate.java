package com.example.main.delegate.interfaces;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.example.main.model.Physicalspace;

public interface PhysicalSpaceDelegate {
	
	public Physicalspace getPhysicalSpace(long id);
	
	public List<Physicalspace> getAllPhysicalSpaces();
	
	public HttpStatus createPhysicalSpace(Physicalspace physicalSpace);
	
	public void updatePhysicalSpace(long id, Physicalspace physicalSpace);
	
	public void deletePhysicalSpace(long id);
	
	public List<Physicalspace> findByName(String name);
	
	public List<Physicalspace> findByExtId(String extId);
	
	public List<Physicalspace> findPhysicalSpacesWithADateRange(Date startDate, Date endDate);

}
