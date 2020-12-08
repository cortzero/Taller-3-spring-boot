package com.example.main.daos.interfaces;

import java.util.Date;
import java.util.List;

import com.example.main.model.Communityinstance;

public interface CommunityinstanceDAO {
	
	public void save(Communityinstance communityinstance);
	
	public void update(Communityinstance communityinstance);
	
	public void delete(Communityinstance communityinstance);
	
	public Communityinstance findById(long id);
	
	public List<Communityinstance> findByDateRange(Date startDate, Date endDate);
	
	public List<Communityinstance> findAll();

}
