package org.java.his.equipment.web;

import org.java.his.equipment.pojo.MaterialRequisition;
import org.java.his.equipment.pojo.MedicalWarehouse;
import org.java.his.equipment.service.MaterialRequisitionService;
import org.java.his.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 领料单
 */
@RestController
@RequestMapping("materialRequisition")
public class MaterialRequisitionController {

    @Autowired
    private MaterialRequisitionService materialRequisitionService;

    /**
     * 添加领料单
     * 直接访问地址: http://localhost:11000/materialRequisition
     * 网关访问地址:http://api.his.com/api/equipment/materialRequisition
     * @param map
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> addMaterialRequisition(@RequestParam Map map){
        materialRequisitionService.addMaterialRequisition(map);
        return ResponseEntity.ok().build();
    }

    /**
     * 加载所有领料单
     * 直接访问地址: http://localhost:11000/materialRequisition?page=1&limit=20
     * 网关访问地址: http://api.his.com/api/equipment/materialRequisition?page=1&limit=20
     * @return
     */
    @GetMapping
    public ResponseEntity<PageResult<Map>> loadAll(
                                @RequestParam("page") Integer page,
                                @RequestParam("limit") Integer limit,
                                String itemName,
                                String category){
        PageResult<Map> pageResult = materialRequisitionService.loadAll(itemName,category,page, limit);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 删除领料单
     * 直接访问地址: http://localhost:11000/materialRequisition/del/xxx
     * 网关访问地址: http://api.his.com/api/equipment/materialRequisition/del/xxx
     * @param mrId
     * @return
     */
    @DeleteMapping("del/{mrId}")
    public ResponseEntity<Void> delMaterialRequisition(@PathVariable("mrId") String mrId){
        materialRequisitionService.delMaterialRequisition(mrId);
        return ResponseEntity.ok().build();
    }
}
