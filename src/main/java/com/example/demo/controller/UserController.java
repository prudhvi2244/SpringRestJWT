package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.entity.UserRequest;
import com.example.demo.jwt.JWTUtil;
import com.example.demo.service.IUserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private IUserService uservice;	
	
	@Autowired
	private JWTUtil jutil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	

	@PostMapping(value = "/save")
	public ResponseEntity<String> saveUser(@RequestBody User user) {
		String msg = uservice.saveUser(user);
		return new ResponseEntity<String>(msg, HttpStatus.CREATED);
	}

	@PostMapping(value = "/login")
	public ResponseEntity<String> login(@RequestBody UserRequest urequest) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
				(urequest.getUsername(),urequest.getPassword()));
		String token=jutil.generateToken(urequest.getUsername());
		return new ResponseEntity<String>(token, HttpStatus.OK);
	}
}
