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

import com.example.main.delegate.interfaces.CommunityInstanceDelegate;
import com.example.main.model.Communityinstance;

@Component
public class CommunityInstanceDelegateImpl implements CommunityInstanceDelegate {
	
	private static String URL = "http://localhost:8080/rest/communities/";
	
    private RestTemplate restTemplate;
    
    @Autowired
    public CommunityInstanceDelegateImpl(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON));
		messageConverters.add(converter);
		this.restTemplate.setMessageConverters(messageConverters);
    }

	@Override
	public Communityinstance getCommunity(long id) {
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity <Communityinstance> entity = new HttpEntity<Communityinstance>(headers);
	    ResponseEntity<Communityinstance> response = restTemplate.exchange(URL + id, HttpMethod.GET, entity, Communityinstance.class);
	    return response.getBody();
	}

	@Override
	public List<Communityinstance> getAllCommunities() {
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity <List<Communityinstance>> entity = new HttpEntity<>(headers);
	    ResponseEntity<List<Communityinstance>> response = restTemplate.exchange(URL, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Communityinstance>>() {
        });
	    return response.getBody();
	}

	@Override
	public HttpStatus createCommunity(Communityinstance community) {
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    HttpEntity <Communityinstance> entity = new HttpEntity<Communityinstance>(community, headers);
	    ResponseEntity<String> response = restTemplate.exchange(URL + "create", HttpMethod.POST, entity, String.class);
	    return response.getStatusCode();
	}

	@Override
	public void updateCommunity(long id, Communityinstance community) {
		Map<String, Long> params = new HashMap<String, Long>();
	    params.put("id", id);
		restTemplate.put(URL + id, community, params);
	}

	@Override
	public void deleteCommunity(long id) {
		Map<String, Long> params = new HashMap<String, Long>();
	    params.put("id", id);
	    restTemplate.delete(URL + id, params);
	}

	@Override
	public List<Communityinstance> findByDateRange(int sDay, int sMonth, int sYear, int eDay, int eMonth, int eYear) {
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity <List<Communityinstance>> entity = new HttpEntity<>(headers);
	    ResponseEntity<List<Communityinstance>> response = restTemplate.exchange(URL + "/startDate="+sDay+"?"+sMonth+"?"+sYear+"&endDate="+eDay+"?"+eMonth+"?"+eYear, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Communityinstance>>() {
        });
	    return response.getBody();
	}

}
