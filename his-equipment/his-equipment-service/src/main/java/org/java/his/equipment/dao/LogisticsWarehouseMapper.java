package org.java.his.equipment.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.java.his.equipment.pojo.LogisticsWarehouse;
import tk.mybatis.mapper.common.Mapper;

/**
 * 后勤库房
 */
public interface LogisticsWarehouseMapper extends Mapper<LogisticsWarehouse> {

    /**
     * 修改库存数量，根据库存数量-领用数量
     * @param logisticsId
     * @param numberRecipients
     */
    @Update("update logistics_warehouse set inventoryQuantity=inventoryQuantity-#{numberRecipients} where logisticsId=#{logisticsId}")
    public void updateCount(@Param("logisticsId") String logisticsId,
                            @Param("numberRecipients") Integer numberRecipients);

    /**
     * 修改医疗设备库房中的数量
     * @param inventoryQuantity
     * @param logisticsId
     */
    @Update("update logistics_warehouse set inventoryQuantity=inventoryQuantity+#{inventoryQuantity} where logisticsId=#{logisticsId}")
    public void updateInventoryQuantity(@Param("inventoryQuantity") Integer inventoryQuantity,@Param("logisticsId") String logisticsId);

    /**
     * 付款
     * @param logisticsId
     */
    @Update("update logistics_warehouse set ifPayment=1 where logisticsId=#{logisticsId}")
    public void payment(@Param("logisticsId") String logisticsId);
}