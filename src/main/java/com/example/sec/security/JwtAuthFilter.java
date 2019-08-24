package com.example.sec.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthFilter extends OncePerRequestFilter{
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
		String bearerToken=request.getHeader("Authorization");
		String token=bearerToken.substring(7);
		System.out.println(token);
		String subject=jwtTokenProvider.getSubjectFromToken(token);
		UserDetails userDetails=myUserDetailsService.loadUserByUsername(subject);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
		}catch(Exception e) {
			System.err.println("Filter "+e);
		}
		
		filterChain.doFilter(request, response);
	}

}
