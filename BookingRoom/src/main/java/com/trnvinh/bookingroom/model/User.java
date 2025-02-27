package com.trnvinh.bookingroom.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	private Long userId;
	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Boolean isAdmin;
	private String apiToken;
}
