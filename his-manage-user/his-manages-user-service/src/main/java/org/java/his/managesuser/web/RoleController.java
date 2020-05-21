package org.java.his.managesuser.web;

import org.java.his.managesuser.pojo.Role;
import org.java.his.managesuser.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 获得所有职位
     *  直接访问地址: http://localhost:12000/role/query
     *  网关访问地址: http://api.his.com/api/managesuser/role/query
     * @return
     */
    @GetMapping("query")
    public ResponseEntity<List<Role>> findAllRole(){
        List<Role> role = roleService.findAllRole();
        return ResponseEntity.ok(role);
    }

}
