package com.example.demo.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200",allowedHeaders = "*")
public class TestController {

	@GetMapping(value = "/welcome")
	public ResponseEntity<String> welcome(Principal p)
	{
		String msg="Welcome "+p.getName();
		return new ResponseEntity<String>(msg,HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/books")
	public ResponseEntity<List<String>> getAllBooks()
	{
		return new ResponseEntity<List<String>>(Arrays.asList("Java-8","React JS","Angular","AWS"),HttpStatus.OK);
	}
	
}
