package com.sillycat.authcenter.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SillycatHttpClient {
	
	private final RestTemplate restTemplate;
	
	public SillycatHttpClient() {
		this.restTemplate = new RestTemplate();
	}
	
	public String makeHttpRequest(String url) {
		String response = restTemplate.getForObject(url,  String.class);
		return response;
	}
	
}
