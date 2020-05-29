package org.java.his.drugwarehouse.web;

import org.java.his.drugwarehouse.pojo.Drugwarehouse;
import org.java.his.drugwarehouse.service.MedicineWarehouseOutService;
import org.java.his.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 医疗设备出库单
 */
@RestController
@RequestMapping("medicineWarehouseOut")
public class MedicineWarehouseOutController {

    @Autowired
    private MedicineWarehouseOutService medicineWarehouseOutService;

    /**
     * 添加药品入库信息
     * 直接访问地址: http://localhost:10000/medicineWarehouseOut
     * 网关访问地址:http://api.his.com/api/drugwarehouse/medicineWarehouseOut
     * @param map
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> addMedicineWarehouseOut(@RequestParam Map map){
        medicineWarehouseOutService.addMedicineWarehouseOut(map);
        return  ResponseEntity.ok().build();
    }

    /**
     * 加载所有出库信息
     * 直接访问地址: http://localhost:10000/medicineWarehouseOut/list?page=1&limit=20
     * 网关访问地址:http://api.his.com/api/drugwarehouse/medicineWarehouseOut/list?page=1&limit=20
     * @param page
     * @param limit
     * @param drugname
     * @param supplier
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<PageResult<Map>> loadMedicineWarehouseOute(
            @RequestParam("page") Integer page,
             @RequestParam("limit") Integer limit,
            String drugname,
            String supplier){
        PageResult<Map> pageResult = medicineWarehouseOutService.loadMedicineWarehouseOute(page, limit,drugname,supplier);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 根据出库编号，删除出库信息
     * 直接访问地址: http://localhost:10000/medicineWarehouseOut
     * 网关访问地址:http://api.his.com/api/drugwarehouse/medicineWarehouseOut/del/xxx
     * @param mwId
     * @return
     */
    @DeleteMapping("del/{mwId}")
    public ResponseEntity<Void> delMedicineWarehouseOute(@PathVariable("mwId") String mwId){
        System.out.println(mwId);
        medicineWarehouseOutService.delMedicineWarehouseOute(mwId);
        return  ResponseEntity.ok().build();
    }

    /**
     * 根据出库编号,获得出库详情
     * 直接访问地址: http://localhost:10000/medicineWarehouseOut/query/xxx
     * 网关访问地址:http://api.his.com/api/drugwarehouse/medicineWarehouseOut/query
     * @param mwId
     * @return
     */
    @GetMapping("query/{mwId}")
    public ResponseEntity<Map> queryMedicineWarehouseOute(@PathVariable("mwId") String mwId){
        Map map = medicineWarehouseOutService.queryMedicineWarehouseOute(mwId);
        return  ResponseEntity.ok(map);
    }
}
