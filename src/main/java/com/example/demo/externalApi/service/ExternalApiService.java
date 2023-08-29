package com.example.demo.externalApi.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Service
public class ExternalApiService {

	private final String apiUrl = "https://www.codewars.com/api/v1/users/";

	@Autowired
	private RestTemplate restTemplate;

	private static Logger log = Logger.getAnonymousLogger();

	public JSONObject fetchData(){

		try {
			return restTemplate.getForObject(apiUrl, JSONObject.class);

		} catch (Exception abc){
			log.info(abc.getMessage());
			return null;
		}
	}
}
