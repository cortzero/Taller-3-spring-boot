package com.example.main.services.implementations;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.main.daos.interfaces.HatParameterDAO;
import com.example.main.model.HatParameter;
import com.example.main.services.interfaces.HatParamaterService;
import com.example.main.services.interfaces.InstitutionService;

@Service
@ComponentScan("com.example.main.daos.implementations")
public class HatParameterServiceImpl implements HatParamaterService {

	private HatParameterDAO hatParameterDAO;
	private InstitutionService instService;

	@Autowired
	public HatParameterServiceImpl(HatParameterDAO hatParameterDAO, InstitutionService instService) {
		this.hatParameterDAO = hatParameterDAO;
		this.instService = instService;
	}

	@Override
	public void save(HatParameter hatParameter) throws NoSuchElementException {
		
		hatParameterDAO.save(hatParameter);
		
	}

	@Override
	public void update(HatParameter hatParameter) throws NoSuchElementException {

		hatParameterDAO.update(hatParameter);

	}

	@Override
	public void delete(HatParameter hatParameter) {

		hatParameterDAO.delete(hatParameter);

	}

	@Override
	public HatParameter findById(long id) {

		return hatParameterDAO.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<HatParameter> findAll() {

		return hatParameterDAO.findAll();
	}
	
	private void checkConditions(HatParameter hatParameter) throws NoSuchElementException {
		if(instService.findById(hatParameter.getInstitution().getInstId()) == null) {
			throw new NoSuchElementException("No existe la institución especificada.");
		}
	}

	@Override
	public boolean contains(HatParameter hatParameter) {
		return hatParameterDAO.contains(hatParameter);
	}

}
