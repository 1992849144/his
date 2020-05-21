package org.java.his.drugwarehouse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 药品报损
 */
@Table(name = "sys_breakage")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysBreakage implements Serializable {

    @Id
    private String bid;  //药品报损编号
    private String id;  //药品入库编号
    private Integer bnumberreported;  //报损数量
    private Double bzerocoefficient;  //报损数量金额
    private String reportedcauses; //报损原因
    private Date badjusttime; //报损时间
    private Integer bprocessing;  //是否处理 1：末处理，2：已处理
    private String boperator; //操作员
    private String bassessor; //审核员
    private String bauditinfo; //审核意见
    private Date baudittime; //审核时间
}
