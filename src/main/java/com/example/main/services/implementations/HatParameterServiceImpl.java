package com.example.main.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.example.main.daos.interfaces.HatParameterDAO;
import com.example.main.model.HatParameter;
import com.example.main.services.interfaces.HatParamaterService;
import com.example.main.services.interfaces.InstitutionService;

@Service
@ComponentScan("com.example.main.daos.implementations")
public class HatParameterServiceImpl implements HatParamaterService {

	private HatParameterDAO hatParameterDAO;
	private InstitutionService institutionService;

	@Autowired
	public HatParameterServiceImpl(HatParameterDAO hatParameterDAO, InstitutionService institutionService) {
		this.hatParameterDAO = hatParameterDAO;
		this.institutionService = institutionService;
	}

	@Override
	public void save(HatParameter hatParameter) {
		
		if(institutionService.isPresent(hatParameter.getInstitution()))
		hatParameterDAO.save(hatParameter);
		
	}

	@Override
	public void update(HatParameter hatParameter) {

		if(institutionService.isPresent(hatParameter.getInstitution()))
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
	public List<HatParameter> findAll() {

		return hatParameterDAO.findAll();
	}

}
