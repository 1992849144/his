package org.java.his.drugwarehouse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 药品入库
 */

@Table(name = "sys_drugwarehouse")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Drugwarehouse implements Serializable {
    @Id
    private String id;  //药品入库编号
    private String drugname; //药品名称
    private String localitygrowth; //药品产地
    private String drugspecifications; //药品规格
    private String supplier; //供货单位
    private String documentnumber;  //单据号码
    private Integer drugbatchnumber; //药品批号
    private Double retailprice; //零售单价
    private Double wholesaleprice; //批发单价
    private String zerocoefficient; //批零系数
    private Integer purchasequantity; //购入数量
    private Double purchaseprice; //购入价
    private Double purchaseamount; //购入金额
    private Integer receipt; //入库量
    private Double netpoor; //进销差
    private Integer discount; //扣率
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expirydate; //失效日期
}