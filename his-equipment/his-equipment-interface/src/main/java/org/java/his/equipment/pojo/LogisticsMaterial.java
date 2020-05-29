package org.java.his.equipment.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 后勤领料单
 */
@Table(name = "logistics_material")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogisticsMaterial {

    @Id
    @Column(name = "lmId")
    private String lmId;  //领料单编号

    @Column(name = "logisticsId")
    private String logisticsId;  //后勤库房编号

    @Column(name = "dId")
    private String dId;  //部门编号

    @Column(name = "departmentsId")
    private String departmentsId;  //科室编号

    @Column(name = "recipientsWay")
    private String recipientsWay;  //领用方式

    @Column(name = "numberRecipients")
    private Integer numberRecipients;  //领用数量

    @Column(name = "time")
    private Date time;  //领用时间
}