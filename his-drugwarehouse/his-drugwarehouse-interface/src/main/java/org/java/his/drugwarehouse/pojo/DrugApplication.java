package org.java.his.drugwarehouse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 药品申请
 */
@Table(name = "sys_drugApplication")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrugApplication {

    @Id
    @Column(name = "daId")
    private String daId;  //申请流水编号

    @Column(name = "drugCatalogueid")
    private String drugCatalogueid;  //'药品名称'

    private String grade;  //规格&等级

    @Column(name = "retailUnit")
    private String retailUnit;  //'零售单位'

    @Column(name = "numberOfApplications")
    private Integer numberOfApplications;  //'申请数量'

    @Column(name = "drugManagementId")
    private Integer drugManagementId;  //'药品种类'
}