package com.trnvinh.bookingroom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trnvinh.bookingroom.mapper.DayOffMapper;
import com.trnvinh.bookingroom.model.DayOff;

@Service
public class DayOffService {
	@Autowired
	private DayOffMapper mapper;
	
	public List<DayOff> getAllDayOffs(){
		return mapper.getAllDayOffs();
	}
	
	public DayOff getDayOffById(Long id) {
		return mapper.getDayOffById(id);
	}
	
	public void insertDayOff(DayOff dayOff) {
		mapper.insertDayOff(dayOff);
	}
	
	public void updateDayOff(DayOff dayOff) {
		mapper.updateDayOff(dayOff);
	}
	
	public void deleteDayOff(Long id) {
		mapper.deleteDayOff(id);
	}
}
