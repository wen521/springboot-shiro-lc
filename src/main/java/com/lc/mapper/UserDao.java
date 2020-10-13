package com.lc.mapper;

import com.lc.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
@Mapper
@Repository
public interface UserDao {
    @Select("select username,password,remark,perms from shirotest where username=#{name}")
    User queryByName(String name);

    @Select("select username,password,remark,perms from shirotest where password=#{password}")
    User queryByPassword(String password);
}
