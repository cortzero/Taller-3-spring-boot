package com.example.main.restservice.interfaces;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;

import com.example.main.exceptions.PhysicalSpaceTypeWithoutInstitutionException;
import com.example.main.exceptions.PhysicalSpaceTypeWithoutNameException;
import com.example.main.model.Physicalspacetype;

public interface PhysicalSpaceTypeRestService {
	
	public ResponseEntity<String> createPhysicalSpaceType(Physicalspacetype physicalSpaceType) throws PhysicalSpaceTypeWithoutNameException, PhysicalSpaceTypeWithoutInstitutionException, NoSuchElementException;
	
	public ResponseEntity<String> updatePhysicalSpaceType(long id, Physicalspacetype physicalSpaceType) throws PhysicalSpaceTypeWithoutNameException, PhysicalSpaceTypeWithoutInstitutionException;
	
	public ResponseEntity<String> deletePhysicalSpaceType(long id);
	
	public Physicalspacetype getPhysicalSpaceType(long id);
	
	public List<Physicalspacetype> getAllPhysicalSpaceTypes();
	
	public List<Physicalspacetype> findByName(String name);
	
	public List<Physicalspacetype> findByExtId(String extId);

}
