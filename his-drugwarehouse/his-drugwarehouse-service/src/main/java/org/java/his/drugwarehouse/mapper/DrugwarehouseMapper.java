package org.java.his.drugwarehouse.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.java.his.drugwarehouse.pojo.Drugwarehouse;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

/**
 * 药品入库
 */
public interface DrugwarehouseMapper extends Mapper<Drugwarehouse> {

    /**
     * 添加入库数量
     * @param drugwarehouse
     */
    @Update("update sys_drugwarehouse set receipt=receipt+#{receipt} where id=#{id}")
    public int putReceipt(Drugwarehouse drugwarehouse);

    /**
     * 药品调价
     * @return
     */
    @Update("update sys_drugwarehouse set retailprice=#{retailprice},wholesaleprice=#{wholesaleprice} where id=#{id}")
    public int putPriceadjustment(Map map);

    /**
     * 修改入库量为0
     */
    @Update("update sys_drugwarehouse set receipt=0 where id=#{id}")
    public void updateReceiptZero(@Param("id") String id);

    /**
     * 库存减去出库量,修改库存
     * @param id
     * @param receipt
     */
    @Update("update sys_drugwarehouse set receipt=receipt-#{receipt} where id=#{id}")
    public void updateCount(@Param("id") String id,
                            @Param("receipt") Integer receipt);

    /**
     * 删除药品,为了药品报损的时候可以查看，只能修改ifdelete为1了
     * @param id
     */
    @Update("update sys_drugwarehouse set ifdelete=1 where id=#{id}")
    public int delDrugwarehouse(@Param("id") String id);
}
