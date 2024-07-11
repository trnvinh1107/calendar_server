package com.trnvinh.bookingroom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trnvinh.bookingroom.model.User;
import com.trnvinh.bookingroom.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	@Autowired
	private UserService service;

	@GetMapping
	public List<User> getAllUsers() {
		return service.getAllUsers();
	}

	@GetMapping("/{userId}")
	public User getUserByUserId(@PathVariable Long userId) {
		return service.getUserByUserId(userId);
	}

	@PostMapping
	public void insertUser(@RequestBody User user) {
		service.insertUser(user);
	}

	@PutMapping("/{userId}")
	public void updateUser(@PathVariable Long userId, @RequestBody User user) {
		user.setUserId(userId);
		service.updateUser(user);
	}

	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable Long userId) {
		service.deleteUser(userId);
	}

	@PostMapping("/login")
	public User login(@RequestBody User loginRequest) {
		String userName = loginRequest.getUserName();
		String password = loginRequest.getPassword();
		return service.login(userName, password);
	}
	
	@PostMapping("/logout/{userId}")
    public void logout(@PathVariable Long userId) {
		service.logout(userId);
    }

	@GetMapping("/check/{userName}")
	public boolean checkUserName(@PathVariable String userName) {
		return service.checkUserNameExists(userName);
	}
}
