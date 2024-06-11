package com.trnvinh.bookingroom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trnvinh.bookingroom.mapper.UserMapper;
import com.trnvinh.bookingroom.model.User;

@Service
public class UserService {
	@Autowired
	private UserMapper mapper;
	
	public List<User> getAllUsers() {
		return mapper.getAllUsers();
	}
	
	public User getUserByUserName(Long userId) {
		return mapper.getUserByUserName(userId);
	}
	
	public void insertUser(User user) {
		mapper.insertUser(user);
	}
	
	public void updateUser(User user) {
		mapper.updateUser(user);
	}
	
	public void deleteUser(Long userId) {
		mapper.deleteUser(userId);
	}
	
	public User login(String userName, String password) {
		return mapper.login(userName, password);
	}
}
