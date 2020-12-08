package com.example.main.services.implementations;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.example.main.daos.interfaces.CommunityinstanceDAO;
import com.example.main.model.Communityinstance;
import com.example.main.services.interfaces.CommunityinstanceService;

@Service
@ComponentScan("com.example.main.daos.implementations")
public class CommunityinstanceServiceImpl implements CommunityinstanceService {
	
	private CommunityinstanceDAO communityDAO;
	
	@Autowired
	public CommunityinstanceServiceImpl(CommunityinstanceDAO communityDAO) {
		this.communityDAO = communityDAO;
	}

	@Override
	@Transactional
	public void save(Communityinstance communityinstance) {
		communityDAO.save(communityinstance);
	}

	@Override
	@Transactional
	public void edit(Communityinstance communityinstance) {
		communityDAO.update(communityinstance);
	}

	@Override
	@Transactional
	public void delete(Communityinstance communityinstance) {
		communityDAO.delete(communityinstance);
	}

	@Override
	public Communityinstance findById(long id) {
		return communityDAO.findById(id);
	}

	@Override
	public List<Communityinstance> findByDateRange(Date startDate, Date endDate) {
		return communityDAO.findByDateRange(startDate, endDate);
	}

	@Override
	public List<Communityinstance> findAll() {
		return communityDAO.findAll();
	}

}
