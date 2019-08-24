package com.example.sec.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sec.model.User;

public interface UserDao extends JpaRepository<User, Integer>{

	User findByName(String username);

}
