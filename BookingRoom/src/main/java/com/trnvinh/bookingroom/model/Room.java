package com.trnvinh.bookingroom.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
	private Long id;
	private String name;
	private int capacity;
	private String description; 
	private Boolean isActivity;
}
