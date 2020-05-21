package org.java.his.managesuser.service;

import org.java.his.managesuser.dao.RoleMapper;
import org.java.his.managesuser.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 获得所有职位
     * @return
     */
    public List<Role> findAllRole(){
        //获得所有职位
        return roleMapper.selectAll();
    }
}
