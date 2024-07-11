package com.trnvinh.bookingroom.model;
 
import java.time.LocalDateTime; 

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRoom {
	private Long id;
	private Long userId;
	private Long roomId;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String description; 
	private String color;
}
