package com.example.main.delegate.implementations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.main.delegate.interfaces.PhysicalSpaceTypeDelegate;
import com.example.main.model.Physicalspacetype;

@Component
public class PhysicalSpaceTypeDelegateImpl implements PhysicalSpaceTypeDelegate {

	private static String URL = "http://localhost:8081/rest/physicalspacetype/";
	
    private RestTemplate restTemplate;

    @Autowired
    public PhysicalSpaceTypeDelegateImpl(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON));
		messageConverters.add(converter);
		this.restTemplate.setMessageConverters(messageConverters);
    }

	@Override
	public Physicalspacetype getPhysicalSpaceType(long id) {
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity <Physicalspacetype> entity = new HttpEntity<Physicalspacetype>(headers);
	    ResponseEntity<Physicalspacetype> response = restTemplate.exchange(URL + id, HttpMethod.GET, entity, Physicalspacetype.class);
	    return response.getBody();
	}

	@Override
	public List<Physicalspacetype> getAllPhysicalSpaceTypes() {
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity <List<Physicalspacetype>> entity = new HttpEntity<>(headers);
	    ResponseEntity<List<Physicalspacetype>> response = restTemplate.exchange(URL, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Physicalspacetype>>() {
        });
	    return response.getBody();
	}

	@Override
	public HttpStatus createPhysicalSpaceType(Physicalspacetype physicalSpaceType) {
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    HttpEntity <Physicalspacetype> entity = new HttpEntity<Physicalspacetype>(physicalSpaceType, headers);
	    ResponseEntity<String> response = restTemplate.exchange(URL + "create", HttpMethod.POST, entity, String.class);
	    return response.getStatusCode();
	}

	@Override
	public void updatePhysicalSpaceType(long id, Physicalspacetype physicalSpaceType) {
		Map<String, Long> params = new HashMap<String, Long>();
	    params.put("id", id);
		restTemplate.put(URL + id, physicalSpaceType, params);
	}

	@Override
	public void deletePhysicalSpaceType(long id) {
		Map<String, Long> params = new HashMap<String, Long>();
	    params.put("id", id);
	    restTemplate.delete(URL + id, params);
	}

	@Override
	public List<Physicalspacetype> findByName(String name) {
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity <List<Physicalspacetype>> entity = new HttpEntity<>(headers);
	    ResponseEntity<List<Physicalspacetype>> response = restTemplate.exchange(URL + "find_by_name/" + name, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Physicalspacetype>>() {
        });
	    return response.getBody();
	}

	@Override
	public List<Physicalspacetype> findByExtId(String extId) {
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity <List<Physicalspacetype>> entity = new HttpEntity<>(headers);
	    ResponseEntity<List<Physicalspacetype>> response = restTemplate.exchange(URL + "find_by_extid/" + extId, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Physicalspacetype>>() {
        });
	    return response.getBody();
	}
	
}
