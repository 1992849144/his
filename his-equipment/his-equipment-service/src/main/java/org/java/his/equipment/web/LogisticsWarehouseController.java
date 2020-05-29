package org.java.his.equipment.web;

import org.apache.ibatis.annotations.Param;
import org.java.his.equipment.pojo.LogisticsWarehouse;
import org.java.his.equipment.service.LogisticsWarehouseService;
import org.java.his.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 后勤库房
 */
@RestController
@RequestMapping("logistics")
public class LogisticsWarehouseController {

    @Autowired
    private LogisticsWarehouseService logisticsWarehouseService;

    /**
     * 后勤库房添加
     * 直接访问地址: http://localhost:11000/logistics
     * 网关访问地址: http://api.his.com/api/equipment/logistics
     * @param logisticsWarehouse
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> addLogistics(LogisticsWarehouse logisticsWarehouse){
        logisticsWarehouseService.addLogistics(logisticsWarehouse);
        return ResponseEntity.ok().build();
    }

    /**
     * 显示所有后勤库房
     * 直接访问地址: http://localhost:11000/logistics/list
     * 网关访问地址: http://api.his.com/api/equipment/logistics/list?page=1&limit=20
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<PageResult<LogisticsWarehouse>> loadAll(
                                        @RequestParam("page") Integer page,
                                        @RequestParam("limit") Integer limit,
                                        String itemName){
        PageResult<LogisticsWarehouse> pageResult = logisticsWarehouseService.loadAll(itemName,page, limit);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 根据库房编号删除
     * 直接访问地址: http://localhost:11000/logistics/del/xxx
     * 网关访问地址: http://api.his.com/api/equipment/logistics/del/xxx
     * @return
     */
    @DeleteMapping("del/{logisticsId}")
    public ResponseEntity<Void> delLogistics(@PathVariable("logisticsId") String logisticsId){
        logisticsWarehouseService.delLogistics(logisticsId);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据名称，获得后勤库房详情
     * 直接访问地址: http://localhost:11000/logistics/query/xxx
     * 网关访问地址: http://api.his.com/api/equipment/logistics/query/xxx
     * @return
     */
    @GetMapping("query/{itemNames}")
    public ResponseEntity<LogisticsWarehouse> quertByName(@PathVariable("itemNames") String itemNames){
        LogisticsWarehouse logisticsWarehouse = logisticsWarehouseService.quertByName(itemNames);
        return ResponseEntity.ok(logisticsWarehouse);
    }

    /**
     * 修改医疗设备库房中的数量
     * 直接访问地址: http://localhost:11000/logistics
     * 网关访问地址: http://api.his.com/api/equipment/logistics
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateInventoryQuantity(Integer inventoryQuantity,String logisticsId){
        logisticsWarehouseService.updateInventoryQuantity(inventoryQuantity,logisticsId);
        return ResponseEntity.ok().build();
    }

    /**
     * 显示所有付款登记，按照末付款进行排序
     * 直接访问地址: http://localhost:11000/logistics/order
     * 网关访问地址: http://api.his.com/api/equipment/logistics/order?page=1&limit=20
     * @return
     */
    @GetMapping("order")
    public ResponseEntity<PageResult<LogisticsWarehouse>> order(
            @RequestParam("page") Integer page,
            @RequestParam("limit") Integer limit,
            String itemName){
        PageResult<LogisticsWarehouse> pageResult = logisticsWarehouseService.order(itemName,page, limit);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 付款
     * 直接访问地址: http://localhost:11000/logistics/order
     * 网关访问地址: http://api.his.com/api/equipment/logistics/payment
     * @param logisticsId
     * @return
     */
    @PutMapping("payment/{logisticsId}")
    public ResponseEntity<Void> payment(@PathVariable("logisticsId") String logisticsId){
        System.out.println(logisticsId);
        logisticsWarehouseService.payment(logisticsId);
        return ResponseEntity.ok().build();
    }
}
