package com.trnvinh.bookingroom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.trnvinh.bookingroom.model.User;

@Mapper
public interface UserMapper {
	@Select("SELECT * FROM users WHERE userName != 'admin'")
	public List<User> getAllUsers();

	@Select("SELECT * FROM users WHERE userId = #{userId}")
	public User getUserByUserId(Long userId);

	@Insert("INSERT INTO users(userName, firstName, lastName, email, password, isAdmin,apiToken) VALUES(#{userName}, #{firstName}, #{lastName}, #{email}, #{password}, #{isAdmin},#{apiToken})")
	public void insertUser(User user);

	@Update("UPDATE users SET userName = #{userName}, firstName = #{firstName}, lastName = #{lastName}, email = #{email}, password = #{password}, isAdmin = #{isAdmin}, apiToken = #{apiToken} WHERE userId = #{userId}")
	public void updateUser(User user);
	
	@Delete("DELETE users WHERE userId = #{userId}")
	public void deleteUser(Long userId);
	
	@Select("SELECT * FROM users WHERE userName = #{userName} and password = #{password}")
	public User login(String userName, String password);
	
	@Select("SELECT COUNT(*) > 0 FROM users WHERE userName = #{userName}")
	public boolean existsByUserName(String userName);
	
	 @Select("SELECT * FROM users WHERE userName = #{userName}")
	 public User findByUserName(String userName);
	 @Select("SELECT * FROM users WHERE apiToken = #{apiToken}")
	 public User findByApiToken(String apiToken);
	 @Update("UPDATE users SET apiToken = NULL WHERE userId = #{userId}")
	 public void logout(Long userId);
}
