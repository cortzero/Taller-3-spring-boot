package com.example.main.services.implementations;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.main.daos.interfaces.PhysicalspaceDAO;
import com.example.main.exceptions.No5DigitsExternalIDException;
import com.example.main.model.Physicalspace;
import com.example.main.services.interfaces.InstitutionCampusService;
import com.example.main.services.interfaces.PhysicalSpaceService;
import com.example.main.services.interfaces.PhysicalSpaceTypeService;

@Service
public class PhysicalSpaceServiceImpl implements PhysicalSpaceService {
	
	private PhysicalspaceDAO physicalSpaceDAO;
	private InstitutionCampusService campusService;
	private PhysicalSpaceTypeService physSpTypeService;
	
	@Autowired
	public PhysicalSpaceServiceImpl(PhysicalspaceDAO physicalSpaceDAO, InstitutionCampusService campusService, PhysicalSpaceTypeService physSpTypeService) {
		this.physicalSpaceDAO = physicalSpaceDAO;
		this.campusService = campusService;
		this.physSpTypeService = physSpTypeService;
	}

	@Override
	@Transactional
	public void save(Physicalspace physSp) throws No5DigitsExternalIDException, NoSuchElementException, NumberFormatException {
		checkConditions(physSp);
		physicalSpaceDAO.save(physSp);
	}

	@Override
	@Transactional
	public void update(Physicalspace physSp) throws No5DigitsExternalIDException, NoSuchElementException, NumberFormatException {
		checkConditions(physSp);
		physicalSpaceDAO.update(physSp);
	}

	@Override
	@Transactional
	public void delete(Physicalspace physSp) {
		physicalSpaceDAO.delete(physSp);
	}

	@Override
	public Physicalspace findById(long id) throws NoSuchElementException {
		return physicalSpaceDAO.findById(id);
	}

	@Override
	public List<Physicalspace> findByName(String name) {
		return physicalSpaceDAO.findByName(name);
	}

	@Override
	public List<Physicalspace> findByExtId(String extId) {
		return physicalSpaceDAO.findByExtId(extId);
	}

	@Override
	public List<Physicalspace> findPhysicalSpacesWithADateRange(Date startDate, Date endDate) {
		return physicalSpaceDAO.findPhysicalSpacesBetweenDates(startDate, endDate);
	}

	@Override
	public List<Physicalspace> findAll() {
		return physicalSpaceDAO.findAll();
	}

	private void checkConditions(Physicalspace physSp) throws No5DigitsExternalIDException, NoSuchElementException, NumberFormatException {
		if(physSp.getInstitutioncampus() == null) {
			throw new NoSuchElementException("No se seleccionó un campus.");
		}
		if(physSp.getPhysicalspacetype() == null) {
			throw new NoSuchElementException("No se seleccionó un tipo de espacio físico.");
		}
		if(campusService.findById(physSp.getInstitutioncampus().getInstcamId()) == null) {
			throw new NoSuchElementException("No existe este campus.");
		}
		if(physSpTypeService.findById(physSp.getPhysicalspacetype().getPhyspctypeId()) == null) {
			throw new NoSuchElementException("No existe este tipo de espacio físico.");
		}
		if(physSp.getPhyspcExtid().length() < 5 && physSp.getPhyspcExtid().length() > 0 || physSp.getPhyspcExtid().length() < 0 || physSp.getPhyspcExtid().length() > 5 ) {
			throw new No5DigitsExternalIDException("El external id debe tener 5 dígitos.");
		}
		if(!physSp.getPhyspcExtid().isEmpty() && physSp.getPhyspcExtid().length() == 5) {
			Integer.parseInt(physSp.getPhyspcExtid());
		}
	}

}
