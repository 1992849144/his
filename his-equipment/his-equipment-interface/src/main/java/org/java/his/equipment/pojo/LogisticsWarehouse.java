package org.java.his.equipment.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 后勤库房
 */
@Table(name = "logistics_warehouse")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogisticsWarehouse {

    @Id
    @Column(name = "logisticsId")
    private String logisticsId;  //后勤库房编号

    @Column(name = "itemNames")
    private String itemNames;  //物品名称

    @Column(name = "colour")
    private String colour;  //颜色

    @Column(name = "materialman")
    private String materialman;  //材料

    @Column(name = "name")
    private String name;  //厂名

    @Column(name = "distributionCompany")
    private String distributionCompany;  //经销公司

    @Column(name = "inventoryQuantity")
    private Integer inventoryQuantity;  //入库数量

    @Column(name = "price")
    private Double price;  //进货单价

    @Column(name = "money")
    private Double money;  //进货金额

    @Column(name = "time")
    private Date time;  //入库时间

    @Column(name = "ifPayment")
    private Integer ifPayment;  //是否付款

    @Column(name = "self_control")
    private Integer selfControl;  //自制

    private Double weight; //重量

}
