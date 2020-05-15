package org.java.his.drugwarehouse.web;

import org.java.his.drugwarehouse.pojo.Drugwarehouse;
import org.java.his.drugwarehouse.service.DrugwarehouseService;
import org.java.his.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/drugwarehouse")
public class DrugwarehouseController {

    @Autowired
    private DrugwarehouseService drugwarehouseService;

    /**
     * 新增药品入库
     * 直接访问地址: http://localhost:10000/drugwarehouse/savaDrugwarehouse
     * 网关访问地址:http://api.his.com/api/drugwarehouse/savaDrugwarehouse
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
     * 直接访问地址: http://localhost:10000/drugwarehouse/list?page=1&limit=20
     * 网关访问地址:http://api.his.com/api/drugwarehouse/list?page=1&limit=20
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
     * 直接访问地址: http://localhost:10000/drugwarehouse/xxx
     * 网关访问地址:http://api.his.com/api/drugwarehouse/xxx
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delDrugwarehouse(@PathVariable("id") String id){
        drugwarehouseService.delDrugwarehouse(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据id，获得药品详情
     * 直接访问地址: http://localhost:10000/drugwarehouse/xxx
     * 网关访问地址:http://api.his.com/api/drugwarehouse/xxx
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
     * 直接访问地址: http://localhost:10000/drugwarehouse
     * 网关访问地址:http://api.his.com/api/drugwarehouse
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
     * 直接访问地址: http://localhost:10000/drugwarehouse/putReceipt
     * 网关访问地址:http://api.his.com/api/drugwarehouse/putReceipt
     * @param drugwarehouse
     * @return
     */
    @PutMapping("/putReceipt")
    public ResponseEntity<Void> putReceipt(Drugwarehouse drugwarehouse){
        drugwarehouseService.putReceipt(drugwarehouse);
        return ResponseEntity.ok().build();
    }

    /**
     * 药品调价
     * 直接访问地址: http://localhost:10000/drugwarehouse/putPriceadjustment
     * 网关访问地址:http://api.his.com/api/drugwarehouse/putPriceadjustment
     * @param map
     * @return
     */
    @PutMapping("putPriceadjustment")
    public ResponseEntity<Void> putPriceadjustment(@RequestParam Map map){
        drugwarehouseService.putPriceadjustment(map);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据药品名称，获得药品详情
     * 直接访问地址: http://localhost:10000/drugwarehouse/putPriceadjustment
     * 网关访问地址:http://api.his.com/api/drugwarehouse/query
     * @param drugname
     * @return
     */
    @GetMapping("/query")
    public ResponseEntity<Drugwarehouse> queryDrugName(@RequestParam("drugname") String drugname){
        Drugwarehouse drugwarehouse = drugwarehouseService.queryDrugName(drugname);
        return ResponseEntity.ok(drugwarehouse);
    }
 }
