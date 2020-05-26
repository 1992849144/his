package org.java.his.equipment.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 科室
 */
@Table(name = "departments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Departments {

    @Id
    @Column(name = "departmentsId")
    private String departmentsId;  //部门编号

    @Column(name = "departmentsName")
    private String departmentsName; //部门名称

    @Column(name = "dId")
    private String dId;  //部门编号
}
