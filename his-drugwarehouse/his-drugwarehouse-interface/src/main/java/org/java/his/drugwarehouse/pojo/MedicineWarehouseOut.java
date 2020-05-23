package org.java.his.drugwarehouse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 药品出库
 */
@Table(name = "sys_medicineWarehouseOut")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineWarehouseOut {

    @Id
    @Column(name = "mwId")
    private String mwId;  //药品出库编号

    @Column(name = "drugCatalogueId")
    private String drugCatalogueId;  //药品编号

    @Column(name = "deliveryQuantity")
    private Integer deliveryQuantity;  //出库数量

    @Column(name = "issueDate")
    private Date issueDate;  //出库日期

    @Column(name = "dwnumberOfApplications")
    private Integer dwnumberOfApplications;  //申请数量

    @Column(name = "count")
    private Integer count;  //库存数量

    @Column(name = "quantity")
    private Integer quantity;  //实发数量

    @Column(name = "personReceivingTheMedicine")
    private String personReceivingTheMedicine;  //领药人

    @Column(name = "operator")
    private String operator;  //操作人
}