package com.example.main.services.interfaces;

import java.util.Date;
import java.util.List;

import com.example.main.model.Communityinstance;

public interface CommunityinstanceService {
	
	public void save(Communityinstance communityinstance);
	
	public void edit(Communityinstance communityinstance);
		
	public void delete(Communityinstance communityinstance);
	
	public Communityinstance findById(long id);
	
	public List<Communityinstance> findByDateRange(Date startDate, Date endDate);
	
	public List<Communityinstance> findAll();

}
