package org.java.his.equipment.service;

import org.java.his.equipment.dao.DepartmentMapper;
import org.java.his.equipment.pojo.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 部门
 */
@Service
public class DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     *  加载所有部门信息
     * @return
     */
    public List<Department> loadDepartment(){
        return  departmentMapper.selectAll();
    };
}
