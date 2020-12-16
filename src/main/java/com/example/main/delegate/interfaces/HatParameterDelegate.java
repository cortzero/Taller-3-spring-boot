package com.example.main.delegate.interfaces;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.example.main.model.HatParameter;


public interface HatParameterDelegate {
	
	
public HatParameter getHatParameter(long id);
	
	public List<HatParameter> getAllHatParameters();
	
	public HttpStatus createHatParameter(HatParameter hatParameter);
	
	public void updateHatParameter(long id, HatParameter hatParameter);
	
	public void deleteHatParameter(long id);

}
