package com.example.main.delegate.interfaces;

import java.util.List;

import com.example.main.model.Physicalspace;

public interface PhysicalSpaceDelegate {
	
	public Physicalspace getPhysicalSpace(long id);
	
	public List<Physicalspace> getAllPhysicalSpaces();
	
	public void createPhysicalSpace(Physicalspace physicalSpace);
	
	public void updatePhysicalSpace(long id, Physicalspace physicalSpace);
	
	public void deletePhysicalSpace(long id);

}
