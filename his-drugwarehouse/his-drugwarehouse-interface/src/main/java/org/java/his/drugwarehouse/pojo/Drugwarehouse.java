package org.java.his.drugwarehouse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 药品入库
 */

@Table(name = "sys_drugwarehouse")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Drugwarehouse {
    @Id
    private String id;
    private String drugname;
    private String localitygrowth;
    private String drugspecifications;
    private String supplier;
    private String documentnumber;
    private Integer drugbatchnumber;
    private Double retailprice;
    private Double wholesaleprice;
    private String zerocoefficient;
    private Integer purchasequantity;
    private Double purchaseprice;
    private Double purchaseamount;
    private Integer receipt;
    private Double netpoor;
    private Integer discount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expirydate;
}