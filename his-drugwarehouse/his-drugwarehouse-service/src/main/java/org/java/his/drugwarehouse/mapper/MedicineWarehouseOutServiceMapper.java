package org.java.his.drugwarehouse.mapper;

import org.apache.ibatis.annotations.Param;
import org.java.his.drugwarehouse.pojo.MedicineWarehouseOut;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 药品入库
 */
public interface MedicineWarehouseOutServiceMapper extends Mapper<MedicineWarehouseOut> {

    /**
     * 加载所有出库信息
     * @param drugname
     * @param supplier
     * @return
     */
    public List<Map> loadMedicineWarehouseOute(@Param("drugname") String drugname,
                                               @Param("supplier") String supplier);

    /**
     * 据出库编号,获得出库详情
     * @param mwId
     * @return
     */
    public Map queryMedicineWarehouseOute(@Param("mwId") String mwId);
}
