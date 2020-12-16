package com.example.main.delegate.interfaces;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.example.main.model.Communityinstance;

public interface CommunityInstanceDelegate {
	
	public Communityinstance getCommunity(long id);
	
	public List<Communityinstance> getAllCommunities();
	
	public HttpStatus createCommunity(Communityinstance community);
	
	public void updateCommunity(long id, Communityinstance community);
	
	public void deleteCommunity(long id);
	
	public List<Communityinstance> findByDateRange(int sDay, int sMonth, int sYear, int eDay, int eMonth, int eYear);

}
