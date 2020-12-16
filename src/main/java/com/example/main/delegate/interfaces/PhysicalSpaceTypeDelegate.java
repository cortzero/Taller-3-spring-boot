package com.example.main.delegate.interfaces;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.example.main.model.Physicalspacetype;

public interface PhysicalSpaceTypeDelegate {

	public Physicalspacetype getPhysicalSpaceType(long id);
	
	public List<Physicalspacetype> getAllPhysicalSpaceTypes();
	
	public HttpStatus createPhysicalSpaceType(Physicalspacetype physicalSpaceType);
	
	public void updatePhysicalSpaceType(long id, Physicalspacetype physicalSpaceType);
	
	public void deletePhysicalSpaceType(long id);
	
	public List<Physicalspacetype> findByName(String name);
	
	public List<Physicalspacetype> findByExtId(String extId);
	
}
