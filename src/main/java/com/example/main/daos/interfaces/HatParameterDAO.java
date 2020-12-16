package com.example.main.daos.interfaces;

import java.util.List;

import com.example.main.model.HatParameter;

public interface HatParameterDAO {

	public void save(HatParameter hatParameter);

	public void update(HatParameter hatParameter);

	public void delete(HatParameter hatParameter);

	public HatParameter findById(long id);

	public List<HatParameter> findAll();

}
