package org.java.his.drugwarehouse.web.UtilController;

import org.java.his.drugwarehouse.pojo.Drugwarehouse;
import org.java.his.drugwarehouse.pojo.SysDrugPricing;
import org.java.his.drugwarehouse.service.SysBreakageService;
import org.java.his.drugwarehouse.service.SysDrugPricingService;
import org.java.his.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 用于药品调价和药品报损的修改时回显的控制器类
 */
@RestController
public class UtilController {

    @Autowired
    private SysDrugPricingService sysDrugPricingService;

    @Autowired
    private SysBreakageService sysBreakageService;

    /**
     * 根据药品调价id，获得药品调价详情
     * 直接访问地址: http://localhost:10000/query/6d79461f6a1149fca79cbbc106b43db6
     * 网关访问地址:http://api.his.com/api/drugwarehouse/query/6d79461f6a1149fca79cbbc106b43db6
     * @param dpid
     * @return
     */
    @GetMapping("query/{dpid}")
    public ResponseEntity<Map> queryDrugPricing(@PathVariable String dpid){
        Map map = sysDrugPricingService.queryDrugPricingByDpid(dpid);
        return  ResponseEntity.ok(map);
    }

    /**
     * 查询除了末处理的药品调价的信息
     * 直接访问地址: http://localhost:10000/list?page=1&limit=20
     * 网关访问地址: http://api.his.com/api/drugwarehouse/list?page=1&limit=20
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<PageResult<Map>> queryAll(
            @RequestParam("page") Integer page,
            @RequestParam("limit") Integer limit,
            String drugname,
            String supplier){
        PageResult<Map> pageResult= sysDrugPricingService.queryAll(page,limit,drugname,supplier);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 根据药品报损id，获得药品报损详情
     * 直接访问地址: http://localhost:10000/queryBreakage/8c6638f1613e4cb0ba867b9347792ac4
     * 网关访问地址:http://api.his.com/api/drugwarehouse/queryBreakage/960bce4c90b2454e968e2c6708fa03c6
     * @param bid
     * @return
     */
    @GetMapping("queryBreakage/{bid}")
    public ResponseEntity<Map> queryBreakage(@PathVariable String bid){
        Map map = sysBreakageService.queryBreakage(bid);
        return  ResponseEntity.ok(map);
    }

    /**
     * 查询除了末处理的药品报损的信息
     * 直接访问地址: http://localhost:10000/queryAllBreakage?page=1&limit=20
     * 网关访问地址: http://api.his.com/api/drugwarehouse/queryAllBreakage?page=1&limit=20
     * @return
     */
    @GetMapping("queryAllBreakage")
    public ResponseEntity<PageResult<Map>> queryAllBreakage(
            @RequestParam("page") Integer page,
            @RequestParam("limit") Integer limit,
            String drugname,
            String supplier){
        PageResult<Map> pageResult= sysBreakageService.queryAllBreakage(page,limit,drugname,supplier);
        return ResponseEntity.ok(pageResult);
    }
}
