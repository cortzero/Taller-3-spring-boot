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

import com.example.main.delegate.interfaces.PhysicalSpaceDelegate;
import com.example.main.model.Physicalspace;

@Component
public class PhysicalSpaceDelegateImpl implements PhysicalSpaceDelegate {

private static String URL = "http://localhost:8080/admin/physicalspace/";
	
    private RestTemplate restTemplate;

    @Autowired
    public PhysicalSpaceDelegateImpl(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_HTML, MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON));
		messageConverters.add(converter);
		this.restTemplate.setMessageConverters(messageConverters);
    }

	@Override
	public Physicalspace getPhysicalSpace(long id) {
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity <Physicalspace> entity = new HttpEntity<>(headers);
	    ResponseEntity<Physicalspace> response = restTemplate.exchange(URL + id, HttpMethod.GET, entity, Physicalspace.class);
	    return response.getBody();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Physicalspace> getAllPhysicalSpaces() {
		ResponseEntity<List> response = restTemplate.getForEntity(URL, List.class);
		return response.getBody();
	}

	@Override
	public void createPhysicalSpace(Physicalspace physicalSpace) {
		restTemplate.postForEntity(URL, physicalSpace, String.class);
	}

	@Override
	public void updatePhysicalSpace(long id, Physicalspace physicalSpace) {
		Map<String, Long> params = new HashMap<String, Long>();
	    params.put("id", id);
		restTemplate.put(URL + id, physicalSpace, params);
	}

	@Override
	public void deletePhysicalSpace(long id) {
		Map<String, Long> params = new HashMap<String, Long>();
	    params.put("id", id);
	    restTemplate.delete(URL + id, params);
	}
	
}
