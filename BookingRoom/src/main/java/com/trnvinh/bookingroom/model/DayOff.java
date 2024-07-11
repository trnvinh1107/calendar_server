package com.trnvinh.bookingroom.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DayOff {
	private Long id;
	private String name;
	private Date dayOff;
	private String description;
	private Boolean isActivity;
}
