package org.java.his.equipment.dao;

import org.apache.ibatis.annotations.Param;
import org.java.his.equipment.pojo.MaterialRequisition;
import org.java.his.equipment.pojo.MedicalWarehouse;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface MaterialRequisitionMapper extends Mapper<MaterialRequisition> {

    /**
     * 加载所有领料单
     * @return
     */
   public List<Map> loadAll(@Param("itemName") String itemName,@Param("category") String category);
}
