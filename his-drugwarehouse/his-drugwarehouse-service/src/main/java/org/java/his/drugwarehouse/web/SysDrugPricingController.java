package org.java.his.drugwarehouse.web;

import org.apache.ibatis.annotations.Delete;
import org.java.auth.pojo.UserInfo;
import org.java.his.drugwarehouse.interceptor.DrugItercetpor;
import org.java.his.drugwarehouse.pojo.Drugwarehouse;
import org.java.his.drugwarehouse.service.DrugwarehouseService;
import org.java.his.drugwarehouse.service.SysDrugPricingService;
import org.java.his.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 药品调价
 */
@RestController
@RequestMapping("drugPricing")
public class SysDrugPricingController {

    @Autowired
    private SysDrugPricingService sysDrugPricingService; //药品调价

    /**
     * 查询所有药品调价末处理的信息
     * 直接访问地址: http://localhost:10000/drugPricing/list/page=1&limit=20
     * 网关访问地址:http://api.his.com/api/drugwarehouse/drugPricing/list?page=1&limit=20
     * @return
     */
    @GetMapping("list/{page}/{limit}")
    public ResponseEntity<PageResult<Map>> quertDrugPricing(
            @PathVariable Integer page,
            @PathVariable Integer limit){
        PageResult<Map> pageResult = sysDrugPricingService.quertDrugPricing(page,limit);

        if (pageResult==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return  ResponseEntity.ok(pageResult);
    }

    /**
     * 修改药品调价信息中 审核人、审核意见、是否通过、审核时间
     * 直接访问地址: http://localhost:10000/drugPricing
     * 网关访问地址:http://api.his.com/api/drugwarehouse/drugPricing
     * @param map
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> putDrugPricing(@RequestParam Map map){
        Integer dpprocessing = Integer.parseInt(map.get("dpprocessing").toString());
        if (dpprocessing==3){
            //只修改药品调价表
            sysDrugPricingService.updateDrugPricing(map);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            //修改成功，之后在修改药品入库表
            sysDrugPricingService.putDrugPricing(map);
            return  ResponseEntity.ok().build();
        }
    }

    /**
     * 删除药品调价信息
     * 直接访问地址: http://localhost:10000/drugPricing/del
     * 网关访问地址:http://api.his.com/api/drugwarehouse/drugPricing/del/xxx
     * @param id
     * @return
     */
    @DeleteMapping("del/{id}")
    public ResponseEntity<Void> delDrugPricing(@PathVariable String id){
        sysDrugPricingService.delDrugPricing(id);
        return ResponseEntity.ok().build();
    }
}
