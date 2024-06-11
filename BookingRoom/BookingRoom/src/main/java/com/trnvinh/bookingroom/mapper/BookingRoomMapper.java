package com.trnvinh.bookingroom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.trnvinh.bookingroom.model.BookingRoom;

@Mapper
public interface BookingRoomMapper {
	@Select("SELECT * FROM bookingroom")
	public List<BookingRoom> getAllBookingRooms();
	
	@Select("SELECT * FROM bookingroom WHERE userId = #{userId} and roomId = #{roomId}")
	public BookingRoom getBookingRoomByUserRoom(Long userId, Long roomId);
	
	@Insert("INSERT INTO bookingroom(userId, roomId, startTime, endTime, description, createdTime, updatedTime) VALUES(#{userId}, #{roomId}, #{startTime}, #{endTime}, #{description}, #{createdTime}, #{updatedTime})")
	public void insertBookingRoom(BookingRoom bookingRoom);
	
	@Update("UPDATE bookingroom SET startTime = #{startTime}, endTime = #{endTime}, description = #{description}, createdTime = #{createdTime}, updatedTime = #{updatedTime} WHERE userId = #{userId} and roomId = #{roomId}")
	public void updateBookingRoom(BookingRoom bookingRoom);
	
	@Delete("DELETE bookingroom WHERE userId = #{userId} and roomId = #{roomId}")
	public void deleteBookingRoom(Long userId, Long roomId);
}
