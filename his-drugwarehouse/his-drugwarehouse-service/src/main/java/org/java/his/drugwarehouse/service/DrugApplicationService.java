package org.java.his.drugwarehouse.service;

import org.java.his.drugwarehouse.mapper.DrugApplicationMapper;
import org.java.his.drugwarehouse.pojo.DrugApplication;
import org.java.his.enums.ShoppingEnums;
import org.java.his.exception.ShoppingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 药品申请
 */
@Service
public class DrugApplicationService {

    @Autowired
    private DrugApplicationMapper drugApplicationMapper;

    public void addDrugApplication(DrugApplication drugApplication){

        //生成uuid
        String uuid = UUID.randomUUID().toString();
        //因为UUID本身为32位只是生成时多了“-”，所以将它们去点就可
        uuid = uuid.replace("-", "");

        //赋值编号
        drugApplication.setDaId(uuid);

        int rows = drugApplicationMapper.insertSelective(drugApplication);

        if (rows==0){
            throw new ShoppingException(ShoppingEnums.DRUGAPPLICATION_ADD_NOT_FOUND);
        }
    }
}
