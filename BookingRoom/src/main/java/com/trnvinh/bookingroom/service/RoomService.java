package com.trnvinh.bookingroom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trnvinh.bookingroom.mapper.RoomMapper;
import com.trnvinh.bookingroom.model.Room;

@Service
public class RoomService {

	@Autowired
	private RoomMapper mapper;
	
	public List<Room> getAllRooms(){
		return mapper.getAllRooms();
	}
	public List<Room> getAllRoomsAdmin(){
		return mapper.getAllRoomsAdmin();
	}
	public Room getRoomById(Long id) {
		return mapper.getRoomById(id);
	}
	
	public void insertRoom(Room room) {
		mapper.insertRoom(room);
	}
	
	public void updateRoom(Room room) {
		mapper.updateRoom(room);
	}
	
	public void deleteRoom(Long id) {
		mapper.deleteRoom(id);
	}
}
