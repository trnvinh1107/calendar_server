package com.trnvinh.bookingroom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trnvinh.bookingroom.mapper.BookingRoomMapper;
import com.trnvinh.bookingroom.model.BookingRoom;

@Service
public class BookingRoomService {
	@Autowired
	BookingRoomMapper mapper;
	
	public List<BookingRoom> gettAllBookingRooms(){
		return mapper.getAllBookingRooms();
	}
	
	public BookingRoom getBookingRoomByUserRoom(Long userId, Long roomId) {
		return mapper.getBookingRoomByUserRoom(userId, roomId);
	}
	
	public void insertBookingRoom(BookingRoom bookingRoom) {
		mapper.insertBookingRoom(bookingRoom);
	}
	public void updateBookingRoom(BookingRoom bookingRoom) {
		mapper.updateBookingRoom(bookingRoom);
	}
	public void deleteBookingRoom(Long userId, Long roomId) {
		mapper.deleteBookingRoom(userId, roomId);
	}
}
