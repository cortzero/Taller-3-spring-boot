package com.example.main.services.implementations;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.main.daos.interfaces.InstitutionDAO;
import com.example.main.exceptions.InstitutionWithoutNameException;
import com.example.main.exceptions.URLWithoutProtocolException;
import com.example.main.model.Institution;
import com.example.main.services.interfaces.InstitutionService;

@Service
@ComponentScan("com.example.main.daos.implementations")
public class InstitutionServiceImpl implements InstitutionService {
	
	private InstitutionDAO institutionDAO;
	
	@Autowired
	public InstitutionServiceImpl(InstitutionDAO institutionDAO) {
		this.institutionDAO = institutionDAO;
	}
	
	@Override
	@Transactional
	public void save(Institution institution) throws URLWithoutProtocolException, InstitutionWithoutNameException {
		checkConditions(institution);
		institutionDAO.save(institution);
	}
  
	@Override
	@Transactional
	public void update(Institution institution) throws URLWithoutProtocolException, InstitutionWithoutNameException {
		checkConditions(institution);
		institutionDAO.update(institution);
	}
	
	@Override
	@Transactional
	public void delete(Institution institution) {
		institutionDAO.delete(institution);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Institution findById(long id) throws NoSuchElementException {
		return institutionDAO.findById(id); 
	}

	@Override
	@Transactional(readOnly = true)
	public List<Institution> findAll() {
		return institutionDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isPresent(Institution institution) {
		return institutionDAO.isPresent(institution);
	}
	
	public void checkConditions(Institution institution) throws InstitutionWithoutNameException, URLWithoutProtocolException {
		if(institution.getInstName() == null || institution.getInstName().isEmpty()) {
			throw new InstitutionWithoutNameException("No se especificó el nombre de la institución");
		}
		if(!institution.getInstAcademicserverurl().startsWith("https://")) { 
			throw new URLWithoutProtocolException("URL sin protocolo.");
		}
	}

}
