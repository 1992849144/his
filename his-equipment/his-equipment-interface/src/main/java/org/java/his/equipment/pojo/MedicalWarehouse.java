package org.java.his.equipment.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "medical_warehouse")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalWarehouse {

    @Id
    @Column(name = "mid")
    private String mid;  //医疗库房编号

    @Column(name = "category")
    private String category;  //类别

    @Column(name = "itemName")
    private String itemName;  //物品名称

    private String colour;  //颜色

    private String materialman;  //材料

    private String name;  //厂名

    @Column(name = "distributionCompany")
    private String distributionCompany;  //经销公司

    @Column(name = "inventoryQuantity")
    private Integer inventoryQuantity;  //入库数量

    private Double price;  //进货单价

    private Double money;  //进货金额

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date time;  //入库时间

    @Column(name = "dateProduction")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String dateProduction;  //出厂日期
}