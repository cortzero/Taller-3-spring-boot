package com.example.main.services.interfaces;

import java.util.List;
import java.util.NoSuchElementException;

import com.example.main.exceptions.PhysicalSpaceTypeWithoutInstitutionException;
import com.example.main.exceptions.PhysicalSpaceTypeWithoutNameException;
import com.example.main.model.Physicalspacetype;

public interface PhysicalSpaceTypeService {
	
	public void save(Physicalspacetype physicalSpaceType) throws PhysicalSpaceTypeWithoutNameException, PhysicalSpaceTypeWithoutInstitutionException, NoSuchElementException;
	
	public void update(Physicalspacetype physicalSpaceType) throws PhysicalSpaceTypeWithoutNameException, PhysicalSpaceTypeWithoutInstitutionException;
	
	public void delete(Physicalspacetype physicalSpaceType);
	
	public Physicalspacetype findById(long id) throws NoSuchElementException;
	
	public List<Physicalspacetype> findByName(String name);
	
	public List<Physicalspacetype> findByExtId(String extId);
	
	public List<Physicalspacetype> findAll();
	
}
