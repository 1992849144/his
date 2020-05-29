package org.java.his.equipment.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.java.his.equipment.pojo.MedicalWarehouse;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 医疗库房
 */
public interface MedicalWarehouseMapper extends Mapper<MedicalWarehouse> {

    /**
     * 根据医疗设备库房编号，修改是否付款：为已付款
     * @param mid
     * @return
     */
    @Update("update medical_warehouse set ifPayment=1 where mid=#{mid}")
    public int updateIfPayment(@Param("mid") String mid);

    /**
     * 加载所有存在医疗设备库房的信息,按末付款医疗设备排序
     * @param mid
     * @return
     */
    public List<MedicalWarehouse> load(@Param("mid") String mid);

    /**
     *
     * @param category
     * @param itemName
     * @return
     */
    public List<MedicalWarehouse> loadAll(@Param("category") String category,
                                          @Param("itemName") String itemName);

    /**
     *
     * @param mid
     * @param numberRecipients
     */
    @Update("update medical_warehouse set inventoryQuantity=inventoryQuantity-#{numberRecipients} where mid=#{mid}")
    public void updateInventoryQuantity(@Param("mid") String mid,
                                        @Param("numberRecipients") Integer numberRecipients);

    /**
     * 修改医疗设备库房中的数量
     * @param inventoryQuantity
     * @param mid
     */
    @Update("update medical_warehouse set inventoryQuantity=inventoryQuantity+#{inventoryQuantity} where mid=#{mid}")
   public void updateCount(@Param("inventoryQuantity") Integer inventoryQuantity, @Param("mid") String mid);
}
