package com.example.main.restservice.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.main.model.Communityinstance;

public interface CommunityInstanceRestService {
	
	public ResponseEntity<String> createCommunity(Communityinstance community);
	
	public ResponseEntity<String> updateCommunity(long id, Communityinstance community);
	
	public ResponseEntity<String> deleteCommunity(long id);
	
	public Communityinstance getCommunity(long id);
	
	public List<Communityinstance> getAllCommunities();
	
	public List<Communityinstance> findByDateRange(int sDay, int sMonth, int sYear, int eDay, int eMonth, int eYear);

}
