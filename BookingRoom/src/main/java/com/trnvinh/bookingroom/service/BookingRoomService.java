package com.trnvinh.bookingroom.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trnvinh.bookingroom.mapper.BookingRoomMapper;
import com.trnvinh.bookingroom.model.BookingRoom;

@Service
public class BookingRoomService {
	@Autowired
	BookingRoomMapper mapper;

	public List<BookingRoom> gettAllBookingRooms() {
		return mapper.getAllBookingRooms();
	}

	public BookingRoom getBookingRoomByUserRoom(Long id) {
		return mapper.getBookingRoomByUserRoom(id);
	}

	public void insertBookingRoom(BookingRoom bookingRoom) {
		mapper.insertBookingRoom(bookingRoom);
	}

	public void updateBookingRoom(BookingRoom bookingRoom) {
		mapper.updateBookingRoom(bookingRoom);
	}

	public void deleteBookingRoom(Long id) {
		mapper.deleteBookingRoom(id);
	}

	public boolean isBookingConflict(Long roomId, LocalDateTime startTime, LocalDateTime endTime, Long exceptedId) {
	    List<BookingRoom> conflictingBookings = mapper.findConflictingBookings(roomId, startTime, endTime, exceptedId);
	    return !conflictingBookings.isEmpty();
	}

	public List<BookingRoom> findByDate(String date) {
		return mapper.findByDate(date);
	}
}
