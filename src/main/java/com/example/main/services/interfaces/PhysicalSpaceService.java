package com.example.main.services.interfaces;

import java.util.NoSuchElementException;
import java.util.Optional;

import com.example.main.exceptions.No5DigitsExternalIDException;
import com.example.main.model.Physicalspace;

public interface PhysicalSpaceService {
	
	public void savePhysicalSpace(Physicalspace physSp) throws No5DigitsExternalIDException, NoSuchElementException, NumberFormatException;
	
	public void editPhysicalSpace(Physicalspace physSp) throws No5DigitsExternalIDException, NoSuchElementException, NumberFormatException;
	
	public void deletePhysicalSpace(Physicalspace physSp);
	
	public Optional<Physicalspace> findById(long id) throws NoSuchElementException;
	
	public Iterable<Physicalspace> findAll();
	
	public long getAmountOfPhysicalSpaces();

}
