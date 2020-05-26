package org.java.his.equipment.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 *领料单
 */
@Table(name = "materialRequisition")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialRequisition {
    @Id
    @Column(name = "mrId")
    private String mrId;  //领料单编号

    @Column(name = "mid")
    private String mid;  //医疗设备库房编号

    @Column(name = "dId")
    private String dId;  //部门编号

    @Column(name = "departmentsId")
    private String departmentsId;  //领用方式

    @Column(name = "recipientsWay")
    private String recipientsWay;  //科室编号

    @Column(name = "numberRecipients")
    private Integer numberRecipients;  //'领用数量'

    @Column(name = "time")
    private Date time;  //'领用时间'
}