package org.java.his.managesuser.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.java.his.managesuser.pojo.Role;
import org.java.his.managesuser.pojo.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RoleMapper extends Mapper<Role> {

//    @Update("update sys_role set name=#{name} where id=#{roleId}")
//    public void updateRole(@Param("roleId") String roleId,@Param("name") String name);
//
//    @Select("select * from sys_role where name=#{roldName}")
//    public List<Role> query(String roldName);
}
