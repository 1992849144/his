package org.java.his.equipment.dao;

import org.apache.ibatis.annotations.Param;
import org.java.his.equipment.pojo.LogisticsMaterial;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 后勤库房领料单
 */
public interface LogisticsMaterialMapper extends Mapper<LogisticsMaterial> {

    /**
     * 显示所有领料单
     * @param itemName
     * @return
     */
   public  List<Map> loadAll(@Param("itemName") String itemName);
}
