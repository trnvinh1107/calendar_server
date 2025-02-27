package com.trnvinh.bookingroom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trnvinh.bookingroom.model.DayOff;
import com.trnvinh.bookingroom.service.DayOffService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("api/v1/dayoff")
@RestController
public class DayOffController {
	@Autowired
	private DayOffService service;
	
	@GetMapping
	public List<DayOff> getAllDayOffs() {
		return service.getAllDayOffs();
	}
	@GetMapping("/admin")
	public List<DayOff> getAllDayOffAdmin() {
		return service.getAllDayOffAdmin();
	}
	
	@GetMapping("/{id}")
	public DayOff getDayOffById(@PathVariable Long id) {
		return service.getDayOffById(id);
	}
	
	@PostMapping
	public void insertDayOff(@RequestBody DayOff dayOff) {
		service.insertDayOff(dayOff);
	}
	
	@PutMapping("/{id}")
	public void updateDayOff(@PathVariable Long id, @RequestBody DayOff dayOff) {
		dayOff.setId(id);
		service.updateDayOff(dayOff);
	}
	
	@DeleteMapping("/{id}")
	public void deleteDayOff(@PathVariable Long id) {
		service.deleteDayOff(id);
	}
	
}
