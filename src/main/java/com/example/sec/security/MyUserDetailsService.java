package com.example.sec.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.sec.dao.UserDao;
import com.example.sec.model.User;

@Service
public class MyUserDetailsService implements UserDetailsService{
	@Autowired
	private UserDao userDao;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userDao.findByName(username);
		MyUserDetails details=new MyUserDetails();
		details.setUser(user);
		System.out.println(details);
		return details;
	}

}
