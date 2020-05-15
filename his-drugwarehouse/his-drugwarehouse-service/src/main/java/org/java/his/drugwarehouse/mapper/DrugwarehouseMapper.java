package org.java.his.drugwarehouse.mapper;

import org.apache.ibatis.annotations.Update;
import org.java.his.drugwarehouse.pojo.Drugwarehouse;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

public interface DrugwarehouseMapper extends Mapper<Drugwarehouse> {

    /**
     * 修改入库数量
     * @param drugwarehouse
     */
    @Update("update sys_drugwarehouse set receipt=receipt+#{receipt} where id=#{id}")
    public int putReceipt(Drugwarehouse drugwarehouse);

    /**
     * 药品调价
     * @return
     */
    @Update("update sys_drugwarehouse set retailprice=#{protretailprice},wholesaleprice=#{nowretailprice} where id=#{id}")
    public int putPriceadjustment(Map map);

}
