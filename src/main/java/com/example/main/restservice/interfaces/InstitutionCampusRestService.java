package com.example.main.restservice.interfaces;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;

import com.example.main.exceptions.CampusWithNoZeroOccupationException;
import com.example.main.exceptions.CampusWithoutNameException;
import com.example.main.model.Institutioncampus;

public interface InstitutionCampusRestService {

public ResponseEntity<String> createCampus(Institutioncampus campus) throws CampusWithoutNameException, CampusWithNoZeroOccupationException, NoSuchElementException;
	
	public ResponseEntity<String> updateCampus(long id, Institutioncampus campus) throws CampusWithoutNameException, CampusWithNoZeroOccupationException, NoSuchElementException;
	
	public ResponseEntity<String> deleteCampus(long id);
	
	public ResponseEntity<Institutioncampus> getCampus(long id);
	
	public ResponseEntity<List<Institutioncampus>> getAllCampus();
	
}
