package com.example.main.restservice.interfaces;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;

import com.example.main.exceptions.No5DigitsExternalIDException;
import com.example.main.model.Physicalspace;

public interface PhysicalSpaceRestService {
	
	public ResponseEntity<String> createPhysicalSpace(Physicalspace physicalSpace) throws No5DigitsExternalIDException, NoSuchElementException, NumberFormatException;
	
	public ResponseEntity<String> updatePhysicalSpace(long id, Physicalspace physicalSpace) throws No5DigitsExternalIDException, NoSuchElementException, NumberFormatException;
	
	public ResponseEntity<String> deletePhysicalSpace(long id);
	
	public Physicalspace getPhysicalSpace(long id);
	
	public List<Physicalspace> getAllPhysicalSpace();
	
	public List<Physicalspace> findByName(String name);
	
	public List<Physicalspace> findByExtId(String extId);
	
	public List<Physicalspace> findPhysicalSpacesWithADateRange(int sDay, int sMonth, int sYear, int eDay, int eMonth, int eYear);

}
