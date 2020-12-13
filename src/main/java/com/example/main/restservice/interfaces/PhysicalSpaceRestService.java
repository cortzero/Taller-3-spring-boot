package com.example.main.restservice.interfaces;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;

import com.example.main.exceptions.No5DigitsExternalIDException;
import com.example.main.model.Physicalspace;

public interface PhysicalSpaceRestService {
	
	public ResponseEntity<String> createPhysicalSpace(Physicalspace physicalSpace) throws No5DigitsExternalIDException, NoSuchElementException, NumberFormatException;
	
	public ResponseEntity<String> updatePhysicalSpace(long id, Physicalspace physicalSpace) throws No5DigitsExternalIDException, NoSuchElementException, NumberFormatException;
	
	public ResponseEntity<String> deletePhysicalSpace(long id);
	
	public ResponseEntity<Physicalspace> getPhysicalSpace(long id);
	
	public ResponseEntity<List<Physicalspace>> getAllPhysicalSpace();

}
