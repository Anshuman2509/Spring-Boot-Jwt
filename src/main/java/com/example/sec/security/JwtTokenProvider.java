package com.example.sec.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
	
	private String secretKey="audi";
	
	public String generateToken(Authentication authentication) {
		MyUserDetails myUserDetails=(MyUserDetails) authentication.getPrincipal();
		return Jwts.builder().setSubject(myUserDetails.getUsername()).signWith(SignatureAlgorithm.HS512,secretKey).compact();
	}
	
	public String getSubjectFromToken(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		}catch(Exception e) {
			System.out.println(e);
		}
		return false;
	}
	

}
