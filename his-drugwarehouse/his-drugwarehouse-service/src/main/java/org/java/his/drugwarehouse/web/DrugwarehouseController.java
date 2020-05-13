package org.java.his.drugwarehouse.web;

import org.apache.ibatis.annotations.Delete;
import org.java.his.drugwarehouse.pojo.Drugwarehouse;
import org.java.his.drugwarehouse.service.DrugwarehouseService;
import org.java.shopping.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * 直接访问地址: http://localhost:10000/drugwarehouse/list
     * 网关访问地址:http://api.his.com/api/drugwarehouse/list
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<PageResult<Drugwarehouse>> loadDrugwarehouse(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit){
        PageResult<Drugwarehouse> pageResult = drugwarehouseService.loadSku(page, limit);
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
 }
