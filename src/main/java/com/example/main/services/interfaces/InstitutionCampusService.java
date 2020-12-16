package com.example.main.services.interfaces;

import java.util.List;
import java.util.NoSuchElementException;

import com.example.main.exceptions.CampusWithNoZeroOccupationException;
import com.example.main.exceptions.CampusWithoutNameException;
import com.example.main.model.Institutioncampus;

public interface InstitutionCampusService {
	
	public void save(Institutioncampus instCampus) throws CampusWithoutNameException, CampusWithNoZeroOccupationException, NoSuchElementException;
	
	public void update(Institutioncampus instCampus) throws CampusWithoutNameException, CampusWithNoZeroOccupationException, NoSuchElementException;
	
	public void delete(Institutioncampus instCampus);
	
	public Institutioncampus findById(long id) throws NoSuchElementException;
	
	public List<Institutioncampus> findAll();
	
	public boolean contains(Institutioncampus instCampus);
}
