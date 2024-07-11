package com.trnvinh.bookingroom.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.trnvinh.bookingroom.mapper.UserMapper;
import com.trnvinh.bookingroom.model.User;

@Service
public class UserService implements UserDetailsService{
//	public class UserService{
	@Autowired
	private UserMapper mapper;

//	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder()

	@Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = mapper.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
            user.getIsAdmin() ? AuthorityUtils.createAuthorityList("ROLE_ADMIN") : AuthorityUtils.createAuthorityList("ROLE_USER"));
    }
	
	public List<User> getAllUsers() {
		return mapper.getAllUsers();
	}

	public User getUserByUserId(Long userId) {
		return mapper.getUserByUserId(userId);
	}

	public void insertUser(User user) {
//		user.setPassword(passwordEncoder.encode(user.getPassword()));
		mapper.insertUser(user);
	}

	public void updateUser(User user) {
//		if (user.getPassword() != null && !user.getPassword().isEmpty()) {
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//        }	
		mapper.updateUser(user);
	}

	public void deleteUser(Long userId) {
		mapper.deleteUser(userId);
	}
	public User findByUserName(String userName) {
		return mapper.findByUserName(userName);
	}
	public User login(String userName, String password) {
	    User user = mapper.findByUserName(userName);
	    if (user != null && (password.equals(user.getPassword()))) {
	        String apiToken = UUID.randomUUID().toString();
	        user.setApiToken(apiToken);
	        user.setIsAdmin(hasRole(user, "ADMIN")); // Set isAdmin based on role
	        mapper.updateUser(user);
	        return user;
	    }
	    return null;
	}
	public User findByApiToken(String apiToken) {
        return mapper.findByApiToken(apiToken);
    }
	public boolean hasRole(User user, String role) {
        // Implement your logic here, e.g., based on isAdmin field
		if ("ADMIN".equalsIgnoreCase(role)) {
	        return user != null && user.getIsAdmin();
	    }
	    // Add other roles as needed
	    return false;
    }
	public void logout(Long userId) {
        mapper.logout(userId);
    }

	public boolean checkUserNameExists(String userName) {
		return mapper.existsByUserName(userName);
	}
}
