package com.example.sec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.sec.dao.UserDao;
import com.example.sec.model.User;
import com.example.sec.security.JwtTokenProvider;

@RestController
public class Controller {
	@Autowired
	private UserDao userDao;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@GetMapping("/hello")
	public String aa() {
		return "Hello World";
	}
	@PostMapping("/register")
	public ResponseEntity<?> save(@RequestBody User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userDao.save(user);
		return ResponseEntity.ok().build();
	}
	@GetMapping("/aa")
	public String hh() {
		return "HH";
	}
	@PostMapping("/login")
	public ResponseEntity<?> signIn(@RequestBody User user){
		Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(),user.getPassword()));
		System.out.println(authentication);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt=jwtTokenProvider.generateToken(authentication);	
		
		return ResponseEntity.ok(jwt);
	}
}
