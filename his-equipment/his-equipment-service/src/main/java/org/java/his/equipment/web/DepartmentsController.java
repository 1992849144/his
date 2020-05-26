package org.java.his.equipment.web;

import org.java.his.equipment.pojo.Departments;
import org.java.his.equipment.service.DepartmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 科室
 */
@RestController
public class DepartmentsController {

    @Autowired
    private DepartmentsService departmentsService;

    /**
     * 根据部门编号，加载相应的科室信息
     * 直接访问地址: http://localhost:11000/query/xxx
     * 网关访问地址:http://api.his.com/api/equipment/query/xxx
     * @param dId
     * @return
     */
    @GetMapping("query/{dId}")
    public ResponseEntity<List<Departments>> queryDepartmentsByDId(@PathVariable("dId") String dId){
        List<Departments> list = departmentsService.queryDepartmentsByDId(dId);
        return ResponseEntity.ok(list);
    }
}
