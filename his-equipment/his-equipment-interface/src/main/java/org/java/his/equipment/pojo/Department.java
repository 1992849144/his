package org.java.his.equipment.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *部门
 */
@Table(name = "department")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    @Id
    @Column(name = "dId")
    private String dId;  //部门编号

    @Column(name = "dNmae")
    private String dNmae; //部门名称
}
