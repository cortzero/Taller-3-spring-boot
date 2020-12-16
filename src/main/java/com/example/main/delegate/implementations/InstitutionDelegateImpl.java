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

import com.example.main.delegate.interfaces.InstitutionDelegate;
import com.example.main.model.Institution;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class InstitutionDelegateImpl implements InstitutionDelegate {

	private static String URL = "http://localhost:8081/rest/institutions/";

	private RestTemplate restTemplate;

	@Autowired
	public InstitutionDelegateImpl(RestTemplateBuilder builder) {
		this.restTemplate = builder.build();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(mapper);
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON));
		messageConverters.add(converter);
		this.restTemplate.setMessageConverters(messageConverters);
	}

	@Override
	public Institution getInstitution(long id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Institution> entity = new HttpEntity<Institution>(headers);
		ResponseEntity<Institution> response = restTemplate.exchange(URL + id, HttpMethod.GET, entity,
				Institution.class);
		return response.getBody();
	}

	@Override
	public List<Institution> getAllInstitutions() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<List<Institution>> entity = new HttpEntity<>(headers);
		ResponseEntity<List<Institution>> response = restTemplate.exchange(URL, HttpMethod.GET, entity,
				new ParameterizedTypeReference<List<Institution>>() {
				});
		return response.getBody();
	}

	@Override
	public HttpStatus createInstitution(Institution institution) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Institution> entity = new HttpEntity<Institution>(institution, headers);
		ResponseEntity<String> response = restTemplate.exchange(URL + "create", HttpMethod.POST, entity, String.class);
		return response.getStatusCode();
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
