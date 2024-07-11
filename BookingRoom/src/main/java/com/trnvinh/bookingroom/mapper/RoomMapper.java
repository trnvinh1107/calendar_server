package com.trnvinh.bookingroom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.trnvinh.bookingroom.model.Room;

@Mapper
public interface RoomMapper {

	@Select("SELECT * FROM rooms")
	public List<Room> getAllRoomsAdmin();
	
	@Select("SELECT * FROM rooms WHERE isActivity = TRUE")
	public List<Room> getAllRooms();
	@Select("SELECT * FROM rooms WHERE id = #{id}")
	public Room getRoomById(Long id);
	
	@Insert("INSERT INTO rooms(name, capacity, description, isActivity) VALUES(#{name},#{capacity},#{description},#{isActivity})")
	public void insertRoom(Room room);
	
	@Update("UPDATE rooms SET name = #{name}, capacity = #{capacity}, description = #{description}, isActivity = #{isActivity} WHERE id = #{id}")
	public void updateRoom(Room room);
	
	@Delete("DELETE rooms WHERE id = #{id}")
	public void deleteRoom(Long id);
}
