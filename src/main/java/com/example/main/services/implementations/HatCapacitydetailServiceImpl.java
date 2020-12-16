package com.example.main.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.example.main.daos.interfaces.HatCapacitydetailDAO;
import com.example.main.model.HatCapacitydetail;
import com.example.main.services.interfaces.HatCapacitydetailService;

@Service
@ComponentScan("com.example.main.daos.implementations")
public class HatCapacitydetailServiceImpl implements HatCapacitydetailService {
	
	public HatCapacitydetailDAO hatCapacitydetailDAO;
	
	@Autowired
	public HatCapacitydetailServiceImpl(HatCapacitydetailDAO hatCapacitydetailDAO) {
		this.hatCapacitydetailDAO = hatCapacitydetailDAO;
	}

	@Override
	public void save(HatCapacitydetail hatCapacitydetail) {
		hatCapacitydetailDAO.save(hatCapacitydetail);
	}

	@Override
	public void update(HatCapacitydetail hatCapacitydetail) {
		hatCapacitydetailDAO.update(hatCapacitydetail);
	}

	@Override
	public void delete(HatCapacitydetail hatCapacitydetail) {
		hatCapacitydetailDAO.delete(hatCapacitydetail);
	}

	@Override
	public HatCapacitydetail findById(long id) {
		return hatCapacitydetailDAO.findById(id);
	}

	@Override
	public List<HatCapacitydetail> findAll() {
		return hatCapacitydetailDAO.findAll();
	}

}
