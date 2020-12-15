package com.example.main.delegate.interfaces;

import java.util.List;

import com.example.main.model.Physicalspacetype;

public interface PhysicalSpaceTypeDelegate {

	public Physicalspacetype getPhysicalSpaceType(long id);
	
	public List<Physicalspacetype> getAllPhysicalSpaceTypes();
	
	public void createPhysicalSpaceType(Physicalspacetype physicalSpaceType);
	
	public void updatePhysicalSpaceType(long id, Physicalspacetype physicalSpaceType);
	
	public void deletePhysicalSpaceType(long id);
	
}
