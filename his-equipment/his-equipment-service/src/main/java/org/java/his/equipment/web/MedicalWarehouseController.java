package org.java.his.equipment.web;

import org.java.his.equipment.pojo.MedicalWarehouse;
import org.java.his.equipment.service.MedicalWarehouseService;
import org.java.his.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 医疗设备库房
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
     * 加载所有存在医疗设备库房的信息,除了退货的设备信息
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

    /**
     * 根据医疗设备编号，删除医疗设备
     * 直接访问地址: http://localhost:11000/medical/del/xxx
     * 网关访问地址:http://api.his.com/api/equipment/medical/del/xxx
     * @param mid
     * @return
     */
    @DeleteMapping("del/{mid}")
    public ResponseEntity<Void> delMedical(@PathVariable("mid") String mid){
        medicalWwarehouseService.delMedical(mid);
        return  ResponseEntity.ok().build();
    }

    /**
     * 加载所有存在医疗设备库房的信息,按末付款医疗设备排序
     * 直接访问地址: http://localhost:11000/medical/listOrder?page=1&limit=20
     * 网关访问地址: http://api.his.com/api/equipment/medical/listOrder?page=1&limit=20
     * @param page
     * @param limit
     * @param mid
     * @return
     */
    @GetMapping("listOrder")
    public ResponseEntity<PageResult<MedicalWarehouse>> loadAllOrder(
            @RequestParam("page") Integer page,
            @RequestParam("limit") Integer limit,
            String mid){
        PageResult<MedicalWarehouse> pageResult = medicalWwarehouseService.loadAllOrder(page,limit,mid);

        return ResponseEntity.ok(pageResult);
    }

    /**
     * 根据医疗设备库房编号，修改是否付款：为已付款
     * 直接访问地址: http://localhost:11000/medical/update/xxx
     * 网关访问地址: http://api.his.com/api/equipment/medical/update/xxx
     * @param mid
     * @return
     */
    @PutMapping("update/{mid}")
    public ResponseEntity<Void> updateIfPayment(@PathVariable("mid") String mid){
        medicalWwarehouseService.updateIfPayment(mid);
        return  ResponseEntity.ok().build();
    }

    /**
     * 退货
     * 直接访问地址: http://localhost:11000/medical/update/xxx
     * 网关访问地址: http://api.his.com/api/equipment/medical/salesReturn
     * @param mid
     * @return
     */
    @PutMapping("salesReturn")
    public ResponseEntity<Void> salesReturn(@RequestParam("mid") String mid,
                                            @RequestParam("salesReturn") String salesReturn){
        medicalWwarehouseService.salesReturn(mid,salesReturn);
        return ResponseEntity.ok().build();
    }

    /**
     * 加载所有已退货的设备信息
     * 直接访问地址: http://localhost:11000/medical/load?page=1&limit=20
     * 网关访问地址: http://api.his.com/api/equipment/medical/load?page=1&limit=20
     * @return
     */
    @GetMapping("load")
    public ResponseEntity<PageResult<MedicalWarehouse>> loadSalesReturn(
                                    @RequestParam("page") Integer page,
                                    @RequestParam("limit") Integer limit,
                                    String mid){
        PageResult<MedicalWarehouse> pageResult = medicalWwarehouseService.loadSalesReturn(mid, page, limit);
        return  ResponseEntity.ok(pageResult);
    }

    /**
     * 修改医疗设备库房中的数量
     * 直接访问地址: http://localhost:11000/medical
     * 网关访问地址: http://api.his.com/api/equipment/medical
     * @param inventoryQuantity
     * @param mid
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateCount(Integer inventoryQuantity,String mid){
        medicalWwarehouseService.updateCount(inventoryQuantity,mid);
        return ResponseEntity.ok().build();
    }
}
