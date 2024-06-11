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

import com.trnvinh.bookingroom.model.BookingRoom;
import com.trnvinh.bookingroom.service.BookingRoomService;

@RestController
@RequestMapping("/api/v1/bookingroom")
public class BookingRoomController {
	@Autowired
	private BookingRoomService service;

	@GetMapping
	public List<BookingRoom> getAllBookingRooms() {
		return service.gettAllBookingRooms();
	}

	@GetMapping("/{userId}/{roomId}")
	public BookingRoom getBookingRoomByUserRoom(@PathVariable Long userId, @PathVariable Long roomId) {
		return getBookingRoomByUserRoom(userId, roomId);
	}

	@PostMapping
	public void insertBookingRoom(@RequestBody BookingRoom bookingRoom) {
		service.insertBookingRoom(bookingRoom);
	}

	@PutMapping("/{userId}/{roomId}")
	public void updateBookingRoom(@PathVariable Long userId, @PathVariable Long roomId,
			@RequestBody BookingRoom bookingRoom) {
		service.updateBookingRoom(bookingRoom);
	}
	
	@DeleteMapping("/{userId}/{roomId}")
	public void deleteBookingRoom(@PathVariable Long userId, @PathVariable Long roomId) {
		service.deleteBookingRoom(userId, roomId);
	}
}
