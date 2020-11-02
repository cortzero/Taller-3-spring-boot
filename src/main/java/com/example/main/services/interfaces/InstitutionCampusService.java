package com.example.main.services.interfaces;

import java.util.NoSuchElementException;
import java.util.Optional;

import com.example.main.exceptions.CampusWithNoZeroOccupationException;
import com.example.main.exceptions.CampusWithoutNameException;
import com.example.main.model.Institutioncampus;

public interface InstitutionCampusService {
	
	public void saveInstitutionCampus(Institutioncampus instCampus) throws CampusWithoutNameException, CampusWithNoZeroOccupationException, NoSuchElementException;
	
	public void editInstitutionCampus(Institutioncampus instCampus) throws CampusWithoutNameException, CampusWithNoZeroOccupationException, NoSuchElementException;
	
	public void deleteInstitutionCampus(Institutioncampus instCampus);
	
	public Optional<Institutioncampus> findById(long id) throws NoSuchElementException;
	
	public Iterable<Institutioncampus> findAll();
	
	public long getNumberOfCampus();
}
