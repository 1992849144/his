package org.java.his.drugwarehouse.web;

import org.java.his.drugwarehouse.pojo.Drugwarehouse;
import org.java.his.drugwarehouse.service.DrugwarehouseService;
import org.java.his.drugwarehouse.service.SysBreakageService;
import org.java.his.drugwarehouse.service.SysDrugPricingService;
import org.java.his.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 药品入库
 */
@RestController
@RequestMapping("/drug")
public class DrugwarehouseController {

    @Autowired
    private DrugwarehouseService drugwarehouseService; //药品入库

    @Autowired
    private SysDrugPricingService sysDrugPricingService; //药品调价

    @Autowired
    private SysBreakageService sysBreakageService;//药品报损



    /**
     * 新增药品入库
     * 直接访问地址: http://localhost:10000/drugwarehouse/drug/savaDrugwarehouse
     * 网关访问地址:http://api.his.com/api/drugwarehouse/drug/savaDrugwarehouse
     * @param drugwarehouse
     * @return
     */
    @PostMapping("savaDrugwarehouse")
    public ResponseEntity<Void> savaDrugwarehouse(Drugwarehouse drugwarehouse){
        drugwarehouseService.savaDrugwarehouse(drugwarehouse);
        return ResponseEntity.ok().build();
    }

    /**
     * 加载所有药品存储在药品库
     * 直接访问地址: http://localhost:10000/drug/list?page=1&limit=20
     * 网关访问地址:http://api.his.com/api/drugwarehouse/drug/list?page=1&limit=20
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<PageResult<Drugwarehouse>> loadDrugwarehouse(
            @RequestParam("page") Integer page,
            @RequestParam("limit") Integer limit,
            String drugname,
            String supplier){
        PageResult<Drugwarehouse> pageResult = drugwarehouseService.loadSku(page, limit,drugname,supplier);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 删除药品
     * 直接访问地址: http://localhost:10000/drug/xxx
     * 网关访问地址:http://api.his.com/api/drugwarehouse/drug/xxx
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delDrugwarehouse(@PathVariable("id") String id){
        Boolean aBoolean= drugwarehouseService.delDrugwarehouse(id);
        if (aBoolean){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * 根据id，获得药品详情
     * 直接访问地址: http://localhost:10000/drug/xxx
     * 网关访问地址:http://api.his.com/api/drugwarehouse/drug/xxx
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Drugwarehouse> findDrugwarehouseId(@PathVariable("id") String id){
        Drugwarehouse drugwarehouse= drugwarehouseService.findDrugwarehouseId(id);
        return  ResponseEntity.ok(drugwarehouse);
    }

    /**
     * 修改药品
     * 直接访问地址: http://localhost:10000/drug
     * 网关访问地址:http://api.his.com/api/drugwarehouse/drug
     * @param drugwarehouse
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateDrugwarehouse(Drugwarehouse drugwarehouse){
        drugwarehouseService.updateDrugwarehouse(drugwarehouse);
        return ResponseEntity.ok().build();
    }

    /**
     * 修改入库数量
     * 直接访问地址: http://localhost:10000/drug/putReceipt
     * 网关访问地址:http://api.his.com/api/drugwarehouse/drug/putReceipt
     * @param drugwarehouse
     * @return
     */
    @PutMapping("/putReceipt")
    public ResponseEntity<Void> putReceipt(Drugwarehouse drugwarehouse){
        drugwarehouseService.putReceipt(drugwarehouse);
        return ResponseEntity.ok().build();
    }

//    /**
//     * 药品调价
//     * 直接访问地址: http://localhost:10000/drug/putPriceadjustment
//     * 网关访问地址:http://api.his.com/api/drugwarehouse/drug/putPriceadjustment
//     * @param map
//     * @return
//     */
//    @PutMapping("putPriceadjustment")
//    public ResponseEntity<Void> putPriceadjustment(@RequestParam Map map){
//        drugwarehouseService.putPriceadjustment(map);
//        return ResponseEntity.ok().build();
//    }

    /**
     * 根据药品名称，获得药品详情
     * 直接访问地址: http://localhost:10000/drug/putPriceadjustment
     * 网关访问地址:http://api.his.com/api/drugwarehouse/drug/query
     * @param drugname
     * @return
     */
    @GetMapping("/query")
    public ResponseEntity<Drugwarehouse> queryDrugName(@RequestParam("drugname") String drugname){
        Drugwarehouse drugwarehouse = drugwarehouseService.queryDrugName(drugname);
        return ResponseEntity.ok(drugwarehouse);
    }

    /**
     * 添加到药品调价表
     * 直接访问地址: http://localhost:10000/drug/addSysDrugPricing
     * 网关访问地址:http://api.his.com/api/drugwarehouse/drug/addSysDrugPricing
     * @param map
     * @return
     */
    @PostMapping("addSysDrugPricing")
    public ResponseEntity<Void> addSysDrugPricing(@RequestParam Map map){
        sysDrugPricingService.addSysDrugPricing(map);
        return ResponseEntity.ok().build();
    }

    /**
     * 添加到药品报损表
     * 直接访问地址: http://localhost:10000/drug/addBreakage
     * 网关访问地址:http://api.his.com/api/drugwarehouse/drug/addBreakage
     * @param map
     * @return
     */
    @PostMapping("addBreakage")
    public ResponseEntity<Void> addBreakage(@RequestParam Map map){
        sysBreakageService.addBreakage(map);
        return ResponseEntity.ok().build();
    }
 }
