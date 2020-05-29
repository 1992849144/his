package org.java.his.drugwarehouse.web;

import org.java.his.drugwarehouse.pojo.DrugApplication;
import org.java.his.drugwarehouse.pojo.Drugwarehouse;
import org.java.his.drugwarehouse.service.DrugApplicationService;
import org.java.his.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 药品申请
 */
@RestController
@RequestMapping("/drugApplication")
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

    /**
     * 显示所有药品申请
     * 直接访问地址: http://localhost:10000/drugApplication/list?page=1&limit=20
     * 网关访问地址:http://api.his.com/api/drugwarehouse/drugApplication/list?page=1&limit=20
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<PageResult<DrugApplication>> loadAll(
                                            @RequestParam("page") Integer page,
                                            @RequestParam("limit") Integer limit,
                                            String daId,
                                            String drugCatalogueId){
        PageResult<DrugApplication> pageResult = drugApplicationService.loadAll(page, limit,daId,drugCatalogueId);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 删除药品申请
     * 直接访问地址: http://localhost:10000/drugApplication/del/xxx
     * 网关访问地址:http://api.his.com/api/drugwarehouse/drugApplication/del/xxx
     * @param daId
     * @return
     */
    @DeleteMapping("del/{daId}")
    public ResponseEntity<Void> delDrugApplication(@PathVariable("daId") String daId){
        System.out.println(daId);
        drugApplicationService.delDrugApplication(daId);
        return ResponseEntity.ok().build();
    }

    /**
     * 修改药品申请
     * 直接访问地址: http://localhost:10000/drugApplication/updateDrugApplication
     * 网关访问地址:http://api.his.com/api/drugwarehouse/drugApplication/updateDrugApplication
     * @param drugApplication
     * @return
     */
    @PutMapping("updateDrugApplication")
    public  ResponseEntity<Void> updateDrugApplication(DrugApplication drugApplication){
        drugApplicationService.updateDrugApplication(drugApplication);
        return ResponseEntity.ok().build();
    }

    /**
     * 采购药品
     * 直接访问地址: http://localhost:10000/drugApplication/purchase
     * 网关访问地址:http://api.his.com/api/drugwarehouse/drugApplication/purchase
     * @param drugApplication
     * @return
     */
    @PutMapping("purchase")
    public ResponseEntity<Void> purchase(DrugApplication drugApplication){
        drugApplicationService.purchase(drugApplication);
        return  ResponseEntity.ok().build();
    }
}
