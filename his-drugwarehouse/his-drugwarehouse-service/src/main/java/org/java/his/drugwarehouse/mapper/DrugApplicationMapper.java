package org.java.his.drugwarehouse.mapper;

import org.java.his.drugwarehouse.pojo.DrugApplication;
import tk.mybatis.mapper.common.Mapper;

/**
 * 药品申请
 */
public interface DrugApplicationMapper extends Mapper<DrugApplication> {

    /**
     * 采购药品
     * @param drugApplication
     */
    public void purchase(DrugApplication drugApplication);
}
