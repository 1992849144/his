package org.java.his.managesuser.dao;

import org.apache.ibatis.annotations.Param;
import org.java.his.managesuser.pojo.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface UserMapper extends Mapper<User> {

    /**
     * 根据用户名获得相关角色信息
     * @param username
     * @return
     */
    public User queryUser(@Param("username") String username);
}
