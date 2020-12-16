package com.example.main.daos.interfaces;

import java.util.List;

import com.example.main.model.Physicalspacetype;

public interface PhysicalspacetypeDAO {
	
	public void save(Physicalspacetype physicalspacetype);
	
	public void update(Physicalspacetype physicalspacetype);
	
	public void delete(Physicalspacetype physicalspacetype);
	
	public Physicalspacetype findById(long id);
	
	public List<Physicalspacetype> findByName(String name);
	
	public List<Physicalspacetype> findByExtId(String extId);
	
	public List<Physicalspacetype> findAll();
	
	public boolean contains(Physicalspacetype physicalspacetype);

}
