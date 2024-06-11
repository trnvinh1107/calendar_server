package com.trnvinh.bookingroom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.trnvinh.bookingroom.model.DayOff;

@Mapper
public interface DayOffMapper {
	@Select("SELECT * FROM dayoff")
	public List<DayOff> getAllDayOffs();
	
	@Select("SELECT * FROM dayoff where id = #{id}")
	public DayOff getDayOffById(Long id);
	
	@Insert("INSERT INTO dayoff(name , dayOff, description) VALUES(#{name} , #{dayOff}, #{description})")
	public void insertDayOff(DayOff dayOff);
	
	@Update("UPDATE dayoff SET name = #{name}, dayOff = #{dayOff}, description = #{description} WHERE id = #{id}")
	public void updateDayOff(DayOff dayOff);
	
	@Delete("DELETE dayoff WHERE id = #{id}")
	public void deleteDayOff(Long id);
}
