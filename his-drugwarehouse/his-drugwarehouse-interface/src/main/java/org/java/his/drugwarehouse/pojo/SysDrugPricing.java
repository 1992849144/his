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
 * 药品调价
 */
@Table(name = "sys_drugpricing")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysDrugPricing implements Serializable {

    @Id
    private String dpid; //药品调价编号
    private String id;  //药品入库编号
    private Double dpprotretailprice;  //现批发价
    private Double dpnowretailprice; //现零售价
    private String duereasons;  //调价原因
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dpadjusttime;  //调价时间
    private Integer dpprocessing; //是否处理 1：末处理，2：已处理
    private String dpoperator; //操作员
    private String dpassessor; //审核员
    private String dpauditinfo; //审核意见
    private Date audittime;//审核时间
}
