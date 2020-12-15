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

import com.example.main.delegate.interfaces.InstitutionDelegate;
import com.example.main.model.Institution;

@Component
public class InstitutionDelegateImpl implements InstitutionDelegate {
	
	private static String URL = "http://localhost:8080/admin/institutions/";
	
    private RestTemplate restTemplate;

    @Autowired
    public InstitutionDelegateImpl(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_HTML, MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON));
		messageConverters.add(converter);
		this.restTemplate.setMessageConverters(messageConverters);
    }

	@Override
	public Institution getInstitution(long id) {
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity <Institution> entity = new HttpEntity<>(headers);
	    ResponseEntity<Institution> response = restTemplate.exchange(URL + id, HttpMethod.GET, entity, Institution.class);
		
//		Map<String, Long> params = new HashMap<String, Long>();
//	    params.put("id", id);
//		ResponseEntity<Institution> response = restTemplate.getForEntity(URL + id, Institution.class, params);
		return response.getBody();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Institution> getAllInstitutions() {
		ResponseEntity<List> response = restTemplate.getForEntity(URL, List.class);
		return response.getBody();
	}

	@Override
	public void createInstitution(Institution institution) {
		restTemplate.postForEntity(URL, institution, String.class);
	}

	@Override
	public void updateInstitution(long id, Institution institution) {
		Map<String, Long> params = new HashMap<String, Long>();
	    params.put("id", id);
		restTemplate.put(URL + id, institution, params);
	}

	@Override
	public void deleteInstitution(long id) {
		Map<String, Long> params = new HashMap<String, Long>();
	    params.put("id", id);
	    restTemplate.delete(URL + id, params);
	}
}
