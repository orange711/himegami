package com.kamikakushipage.mapper;

import com.kamikakushipage.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
public interface UserMapper {
    @Insert("INSERT INTO user(email,password,salt,activation,confirm_code)" +
            "VALUE (#{email},#{password},#{salt},#{activation},#{confirmCode})")
    int insertUser(User user);

    @Select("SELECT email,activation FROM user WHERE confirm_code = #{confirmCode} AND is_valid = 0")
    User selectUserByConfirmCode(@Param("confirmCode") String confirmCode);

    @Update("UPDATE user SET is_valid = 1 WHERE confirm_code = #{confirmCode}")
    int updateUserByConfirmCode(@Param("confirmCode") String confirmCode);

    @Select("SELECT email,password, salt FROM user WHERE email = #{email} AND is_valid = 1")
    List<User> selectUserByEmail(@Param("email") String email);

    @Select("SELECT email,password, salt FROM user WHERE email = #{email} ")
    User selectUserByEmailDuplicate(@Param("email") String email);
}
