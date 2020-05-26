package org.java.his.equipment.service;

import org.java.his.equipment.dao.DepartmentsMapper;
import org.java.his.equipment.pojo.Departments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 科室
 */
@Service
public class DepartmentsService {

    @Autowired
    private DepartmentsMapper departmentsMapper;

    /**
     *根据部门编号，加载相应的科室信息
     * @param dId
     */
    public List<Departments> queryDepartmentsByDId(String dId) {

        //创建查询实例(查询时，可以自己在xml文件中编写 语句查询，也可以使用tkmybatis的方法查询)
        Example example=new Example(Departments.class);

        //封装查询条件(criteria:它可以封装查询条件)
        Example.Criteria criteria = example.createCriteria();

        criteria.andLike("dId",dId);

        List<Departments> list = departmentsMapper.selectByExample(example);

        return list;
    }
}
