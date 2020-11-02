package com.example.main.services.interfaces;

import java.util.NoSuchElementException;
import java.util.Optional;

import com.example.main.exceptions.PhysicalSpaceTypeWithoutInstitutionException;
import com.example.main.exceptions.PhysicalSpaceTypeWithoutNameException;
import com.example.main.model.Physicalspacetype;

public interface PhysicalSpaceTypeService {
	
	public void savePhysicalSpaceType(Physicalspacetype physicalSpaceType) throws PhysicalSpaceTypeWithoutNameException, PhysicalSpaceTypeWithoutInstitutionException, NoSuchElementException;
	
	public void editPhysicalSpaceType(Physicalspacetype physicalSpaceType) throws PhysicalSpaceTypeWithoutNameException, PhysicalSpaceTypeWithoutInstitutionException;
	
	public void deletePhysicalSpaceType(Physicalspacetype physicalSpaceType);
	
	public Optional<Physicalspacetype> findById(long id) throws NoSuchElementException;
	
	public Iterable<Physicalspacetype> findAll();
	
}
