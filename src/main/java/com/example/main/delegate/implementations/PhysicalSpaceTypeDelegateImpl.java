package com.example.main.delegate.implementations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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

	private static String URL = "http://localhost:8080/admin/physicalspacetype/";
	
    private RestTemplate restTemplate;

    @Autowired
    public PhysicalSpaceTypeDelegateImpl(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_HTML, MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON));
		messageConverters.add(converter);
		this.restTemplate.setMessageConverters(messageConverters);
    }

	@Override
	public Physicalspacetype getPhysicalSpaceType(long id) {
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity <Physicalspacetype> entity = new HttpEntity<>(headers);
	    ResponseEntity<Physicalspacetype> response = restTemplate.exchange(URL + id, HttpMethod.GET, entity, Physicalspacetype.class);
	    return response.getBody();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Physicalspacetype> getAllPhysicalSpaceTypes() {
		ResponseEntity<List> response = restTemplate.getForEntity(URL, List.class);
		return response.getBody();
	}

	@Override
	public void createPhysicalSpaceType(Physicalspacetype physicalSpaceType) {
		restTemplate.postForEntity(URL, physicalSpaceType, String.class);
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
	
}
