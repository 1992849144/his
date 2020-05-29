package org.java.his.equipment.web;

import org.java.his.equipment.pojo.LogisticsMaterial;
import org.java.his.equipment.pojo.LogisticsWarehouse;
import org.java.his.equipment.service.LogisticsMaterialService;
import org.java.his.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 后勤库房领料单
 */
@RestController
@RequestMapping("logisticsMaterial")
public class LogisticsMaterialController {

    @Autowired
    private LogisticsMaterialService logisticsMaterialService;

    /**
     * 添加领料单
     * 直接访问地址: http://localhost:11000/logisticsMaterial
     * 网关访问地址: http://api.his.com/api/equipment/logisticsMaterial
     * @param map
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> addLogisticsMaterial(@RequestParam Map map){
        logisticsMaterialService.addLogisticsMaterial(map);
        return  ResponseEntity.ok().build();
    }

    /**
     * 显示所有领料单
     * 直接访问地址: http://localhost:11000/logistics/list
     * 网关访问地址: http://api.his.com/api/equipment/logisticsMaterial/list?page=1&limit=20
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<PageResult<Map>> loadAll(
                                    @RequestParam("page") Integer page,
                                    @RequestParam("limit") Integer limit,
                                    String itemName){
        PageResult<Map> pageResult = logisticsMaterialService.loadAll(itemName, page, limit);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 删除
     * 直接访问地址: http://localhost:11000/logistics/del/xxx
     * 网关访问地址: http://api.his.com/api/equipment/logisticsMaterial/del/xxx
     * @return
     */
    @DeleteMapping("del/{lmId}")
    public ResponseEntity<Void> delLogisticsMaterial(@PathVariable("lmId") String lmId){
        logisticsMaterialService.delLogisticsMaterial(lmId);
       return ResponseEntity.ok().build();
    }
}
