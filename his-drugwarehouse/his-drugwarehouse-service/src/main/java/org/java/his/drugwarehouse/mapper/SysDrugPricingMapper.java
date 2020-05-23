package org.java.his.drugwarehouse.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.java.his.drugwarehouse.pojo.Drugwarehouse;
import org.java.his.drugwarehouse.pojo.SysDrugPricing;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 药品调价
 */
public interface SysDrugPricingMapper extends Mapper<SysDrugPricing> {

    /**
     * 查询所有药品调价末处理的信息
     * @return
     */
    @Select("SELECT * FROM sys_drugpricing d,sys_drugwarehouse sd " +
            "WHERE d.id=sd.id AND dpprocessing=1")
    public List<Map>  quertDrugPricing();

    /**
     * 查询药品调价信息，根据药品编号,不连药品入库表
     * @param id
     */
    @Select("select * from sys_drugpricing where id=#{id}")
    public List<SysDrugPricing> queryDrugPricingByid(String id);

    /**
     * 查询药品调价信息，根据药品编号和是否处理不为1，不连药品入库表
     * @param id
     */
    @Select("select * from sys_drugpricing where id=#{id} and  dpprocessing=1")
    public List<SysDrugPricing> queryDrugPricingByids(String id);

    /**
     * 查询药品调价信息，根据药品编号,连药品入库表,根据药品报损
     * @param dpid
     * @return
     */
    @Select("SELECT * FROM sys_drugpricing d,sys_drugwarehouse sd " +
            "WHERE d.id=sd.id AND d.dpid=#{dpid}")
    public Map queryDrugPricingByDpid(String dpid);

    /**
     * 修改药品调价信息中 审核人、审核意见、是否通过、审核时间，加药品入库表
     * @param map
     * @return
     */
    @Update("update sys_drugpricing set " +
            "dpprocessing=#{dpprocessing},dpassessor=#{dpassessor}" +
            ",dpauditinfo=#{dpauditinfo},audittime=#{audittime}" +
            "where dpid=#{dpid}")
    public int putDrugPricing(Map map);

    /**
     * 修改药品调价信息中 审核人、审核意见、是否通过、审核时间，不加药品入库表
     * @param map
     * @return
     */
    @Update("update sys_drugpricing set " +
            "dpprocessing=#{dpprocessing},dpassessor=#{dpassessor}" +
            ",dpauditinfo=#{dpauditinfo},audittime=#{audittime}" +
            "where dpid=#{dpid}")
    public int updateDrugPricing(Map map);

    /**
     * 查询除了末处理的药品调价的信息
     * @return
     */
    public List<Map> queryAll(@Param("drugname") String drugname,
                              @Param("supplier") String supplier);
}
