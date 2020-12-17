package com.example.main.services.implementations;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.main.daos.interfaces.HatCapacitydetailDAO;
import com.example.main.model.HatCapacitydetail;
import com.example.main.services.interfaces.HatCapacitydetailService;
import com.example.main.services.interfaces.InstitutionCampusService;

@Service
@ComponentScan("com.example.main.daos.implementations")
public class HatCapacitydetailServiceImpl implements HatCapacitydetailService {
	
	private HatCapacitydetailDAO hatCapacitydetailDAO;
	private InstitutionCampusService campusService;
	
	@Autowired
	public HatCapacitydetailServiceImpl(HatCapacitydetailDAO hatCapacitydetailDAO, InstitutionCampusService campusService) {
		this.hatCapacitydetailDAO = hatCapacitydetailDAO;
		this.campusService = campusService;
	}

	@Override
	@Transactional
	public void save(HatCapacitydetail hatCapacitydetail) throws NoSuchElementException {
		hatCapacitydetailDAO.save(hatCapacitydetail);
	}

	@Override
	@Transactional
	public void update(HatCapacitydetail hatCapacitydetail) throws NoSuchElementException{
		hatCapacitydetailDAO.update(hatCapacitydetail);
	}

	@Override
	@Transactional
	public void delete(HatCapacitydetail hatCapacitydetail) {
		hatCapacitydetailDAO.delete(hatCapacitydetail);
	}

	@Override
	@Transactional(readOnly = true)
	public HatCapacitydetail findById(long id) {
		return hatCapacitydetailDAO.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<HatCapacitydetail> findAll() {
		return hatCapacitydetailDAO.findAll();
	}
	
	private void checkConditions(HatCapacitydetail capacity) throws NoSuchElementException {
		if(campusService.findById(capacity.getInstitutioncampus().getInstcamId()) == null) {
			throw new NoSuchElementException("No existe el campus especificado.");

		}
	}

	@Override
	public boolean contains(HatCapacitydetail capacity) {
		return hatCapacitydetailDAO.contains(capacity);
	}

}
