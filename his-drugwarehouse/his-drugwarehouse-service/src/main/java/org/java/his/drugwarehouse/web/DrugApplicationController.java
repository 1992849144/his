package org.java.his.drugwarehouse.web;

import org.java.his.drugwarehouse.pojo.DrugApplication;
import org.java.his.drugwarehouse.service.DrugApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 药品申请
 */
@RestController("drugApplication")
public class DrugApplicationController {

    @Autowired
    private DrugApplicationService drugApplicationService;

    /**
     * 添加药品申请
     * 直接访问地址: http://localhost:10000/drugApplication
     * 网关访问地址:http://api.his.com/api/drugwarehouse/drugApplication
     * @param drugApplication
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> addDrugApplication(DrugApplication drugApplication){
        drugApplicationService.addDrugApplication(drugApplication);
        return ResponseEntity.ok().build();
    }
}
