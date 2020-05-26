package org.java.his.equipment.web;

import org.java.his.equipment.pojo.Department;
import org.java.his.equipment.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 部门
 */
@RestController
@RequestMapping("department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 加载所有部门信息
     * 直接访问地址: http://localhost:11000/department
     * 网关访问地址:http://api.his.com/api/equipment/department
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Department>> loadDepartment(){
        List<Department> departments = departmentService.loadDepartment();
        return ResponseEntity.ok(departments);
    }
}
