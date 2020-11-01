package com.example.main.services.interfaces;

import java.util.NoSuchElementException;
import java.util.Optional;

import com.example.main.exceptions.CampusWithNoZeroOccupationException;
import com.example.main.exceptions.CampusWithoutNameException;
import com.example.main.model.Institutioncampus;

public interface InstitutionCampusService {
	
	public void saveInstitutionCampus(Institutioncampus instCampus) throws CampusWithoutNameException, CampusWithNoZeroOccupationException, NoSuchElementException;
	
	public void editInstitutionCampus(Institutioncampus instCampus) throws CampusWithoutNameException, CampusWithNoZeroOccupationException, NoSuchElementException;
	
	public Optional<Institutioncampus> getInstitutionCampus(long id) throws NoSuchElementException;
	
	public void deleteInstitutionCampus(Institutioncampus instCampus);
	
	public long getNumberOfCampus();
	
	public Iterable<Institutioncampus> findAll();
}
