package org.java.his.equipment.web;

import org.java.his.equipment.pojo.MedicalWarehouse;
import org.java.his.equipment.service.MedicalWarehouseService;
import org.java.his.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 医疗库房
 */
@RestController
@RequestMapping("medical")
public class MedicalWarehouseController {

    @Autowired
    private MedicalWarehouseService medicalWwarehouseService;

    /**
     * 添加设备入医疗库房
     * 直接访问地址: http://localhost:11000/medical/add
     * 网关访问地址:http://api.his.com/api/equipment/medical/add
     * @return
     */
    @PostMapping("add")
    public ResponseEntity<Void> addMedicalWwarehouse(MedicalWarehouse medicalWarehouse){
        medicalWwarehouseService.addMedicalWwarehouse(medicalWarehouse);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据设备名称，获得医疗库房详情
     * 直接访问地址: http://localhost:11000/medical/query/1
     * 网关访问地址:http://api.his.com/api/equipment/medical/query/1
     * @return
     */
    @GetMapping("/query/{itemName}")
    public ResponseEntity<MedicalWarehouse> quertByName(@PathVariable("itemName") String itemName){
        MedicalWarehouse medicalWarehouse = medicalWwarehouseService.quertByName(itemName);
        return ResponseEntity.ok(medicalWarehouse);
    }

    /**
     * 加载所有存在医疗库房的信息
     * 直接访问地址: http://localhost:11000/medical/list?page=1&limit=20
     * 网关访问地址:http://api.his.com/api/equipment/medical/list?page=1&limit=20
     * @param page
     * @param limit
     * @param category
     * @param itemName
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<PageResult<MedicalWarehouse>> loadAll(
                                @RequestParam("page") Integer page,
                                @RequestParam("limit") Integer limit,
                                String category,
                                String itemName){
        PageResult<MedicalWarehouse> pageResult = medicalWwarehouseService.loadAll(page,limit,category, itemName);

        return ResponseEntity.ok(pageResult);
    }


}
