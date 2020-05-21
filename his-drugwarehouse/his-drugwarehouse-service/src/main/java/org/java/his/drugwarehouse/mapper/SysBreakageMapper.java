package org.java.his.drugwarehouse.mapper;

import org.apache.ibatis.annotations.Param;
import org.java.his.drugwarehouse.pojo.SysBreakage;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 药品报损
 */
public interface SysBreakageMapper extends Mapper<SysBreakage> {
    /**
     * 查询所有药品报损末处理的信息
     * @return
     */
    public List<Map> quertBreakage();

    /**
     * 根据药品报损id，获得药品报损id详情
     * @param bid
     */
    public Map quertBreakageByBid(@Param("bid") String bid);

    /**
     * 修改药品调价信息中 审核人、审核意见、是否通过、审核时间，不加药品入库表
     * @param map
     * @return
     */
    public int updateBreakage(Map map);

    /**
     * 修改药品调价信息中 审核人、审核意见、是否通过、审核时间，不加药品入库表
     * @param map
     * @return
     */
    public int putDrugPricing(Map map);

    public List<Map> queryAllBreakage(@Param("drugname") String drugname,@Param("supplier") String supplier);
}
