package org.java.his.drugwarehouse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

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

    @Column(name = "drugCatalogueId")
    private String drugCatalogueId;  //'药品名称'

    private String grade;  //规格&等级

    @Column(name = "retailUnit")
    private String retailUnit;  //'零售单位'

    @Column(name = "numberOfApplications")
    private Integer numberOfApplications;  //'申请数量'

    @Column(name = "drugManagementId")
    private String drugManagementId;  //'药品种类'

    @Column(name = "timeApplication")
    private Date timeApplication;  //'药品种类'

    @Column(name = "purchasePrice")
    private Double purchasePrice;  //'采购单价'

    @Column(name = "money")
    private Double money;  //'采购总金额'

    @Column(name = "time")
    private Date time;  //采购时间

    @Column(name = "whetherPurchase")
    private Integer whetherPurchase;  //是否采购
}