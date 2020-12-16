package com.example.main.services.implementations;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.main.daos.interfaces.InstitutionCampusDAO;
import com.example.main.exceptions.CampusWithNoZeroOccupationException;
import com.example.main.exceptions.CampusWithoutNameException;
import com.example.main.model.Institutioncampus;
import com.example.main.services.interfaces.InstitutionCampusService;
import com.example.main.services.interfaces.InstitutionService;

@Service
@ComponentScan("com.example.main.daos.implementations")
@ComponentScan("com.example.main.services.implementations.InstitutionCampusServiceImpl")
public class InstitutionCampusServiceImpl implements InstitutionCampusService {

	private InstitutionCampusDAO campusDAO;
	private InstitutionService instService;
	
	@Autowired
	public InstitutionCampusServiceImpl(InstitutionCampusDAO campusDAO, InstitutionService instService) {
		this.campusDAO = campusDAO;
		this.instService = instService;
	}

	@Override
	@Transactional
	public void save(Institutioncampus instCampus) throws CampusWithoutNameException, CampusWithNoZeroOccupationException, NoSuchElementException {
		campusDAO.save(instCampus);
	}

	@Override
	@Transactional
	public void update(Institutioncampus instCampus) throws CampusWithoutNameException, CampusWithNoZeroOccupationException, NoSuchElementException {
		campusDAO.update(instCampus);
	}

	@Override
	@Transactional
	public void delete(Institutioncampus instCampus) {
		campusDAO.delete(instCampus);
	}

	@Override
	@Transactional(readOnly = true)
	public Institutioncampus findById(long id) throws NoSuchElementException {
		return campusDAO.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Institutioncampus> findAll() {
		return campusDAO.findAll();
	}
	
	private void checkConditions(Institutioncampus instCampus) throws CampusWithoutNameException, CampusWithNoZeroOccupationException, NoSuchElementException {
		if(instCampus.getInstcamName() == null || instCampus.getInstcamName().isEmpty()) {
			throw new CampusWithoutNameException("No se registró el nombre del campus");
		}
		if(instCampus.getInstcamOccupation().intValue() != 0) {
			throw new CampusWithNoZeroOccupationException("Fomato no válido de la ocupación del campus.");
		}
		if(instService.findById(instCampus.getInstitution().getInstId()) == null) {
			throw new NoSuchElementException("No existe la institución especificada.");
		}
	}

	@Override
	public boolean contains(Institutioncampus instCampus) {
		return campusDAO.contains(instCampus);
	}

}
