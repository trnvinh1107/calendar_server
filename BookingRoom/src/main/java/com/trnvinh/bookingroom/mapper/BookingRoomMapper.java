package com.trnvinh.bookingroom.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.trnvinh.bookingroom.model.BookingRoom;

@Mapper
public interface BookingRoomMapper {
	
	@Select("SELECT * FROM bookingroom")
	public List<BookingRoom> getAllBookingRooms();
	
	@Select("SELECT * FROM bookingroom WHERE id = #{id}")
	public BookingRoom getBookingRoomByUserRoom(Long id);
	
	@Insert("INSERT INTO bookingroom(userId, roomId, startTime, endTime, description, color) VALUES(#{userId}, #{roomId}, #{startTime}, #{endTime}, #{description}, #{color})")
	public void insertBookingRoom(BookingRoom bookingRoom);
	
	@Update("UPDATE bookingroom SET userId = #{userId}, roomId = #{roomId}, startTime = #{startTime}, endTime = #{endTime}, description = #{description}, color = #{color} WHERE id = #{id}")
    public void updateBookingRoom(BookingRoom bookingRoom);
	
	@Delete("DELETE bookingroom WHERE id = #{id}")
	public void deleteBookingRoom(Long id);
	
	@Select("SELECT * FROM bookingroom WHERE roomId = #{roomId} AND (startTime < #{endTime} AND endTime > #{startTime}) AND id != #{exceptedId}")
    List<BookingRoom> findConflictingBookings(@Param("roomId") Long roomId, 
                                              @Param("startTime") LocalDateTime startTime, 
                                              @Param("endTime") LocalDateTime endTime,
                                              @Param("exceptedId") Long exceptedId);
	@Select("SELECT * FROM bookingroom WHERE startTime LIKE CONCAT(#{date}, '%')")
    List<BookingRoom> findByDate(@Param("date") String date);
}
