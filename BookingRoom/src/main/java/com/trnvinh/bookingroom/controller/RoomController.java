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

import com.trnvinh.bookingroom.model.Room;
import com.trnvinh.bookingroom.service.RoomService;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

	@Autowired
	private RoomService service;
	
	@GetMapping
	public List<Room> getAllRooms(){
		return service.getAllRooms();
	}
	@GetMapping("/admin")
	public List<Room> getAllRoomsAdmin(){
		return service.getAllRoomsAdmin();
	}
	@GetMapping("/{id}")
	public Room getRoomById(@PathVariable Long id) {
		return service.getRoomById(id);
	}
	
	@PostMapping
	public void insertRoom(@RequestBody Room room) {
		service.insertRoom(room);
	}
	
	@PutMapping("/{id}")
	public void updateRoom(@PathVariable Long id, @RequestBody Room room) {
		room.setId(id);
		service.updateRoom(room);
	}
	
	@DeleteMapping("/{id}")
	public void deleteRoom(@PathVariable Long id) {
		service.deleteRoom(id);
	}
}
