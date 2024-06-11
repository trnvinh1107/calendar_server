package com.trnvinh.bookingroom.model;

import java.util.Date;

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
	private Date startTime;
	private Date endTime;
	private String description;
	private Date createdTime;
	private Date updatedTime;
}
