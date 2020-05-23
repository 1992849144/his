package org.java.his.drugwarehouse.web;

import org.java.his.drugwarehouse.service.SysBreakageService;
import org.java.his.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 药品报损
 */
@RestController
@RequestMapping("breakage")
public class SysBreakageController {

    @Autowired
    private SysBreakageService sysBreakageService;

    /**
     * 查询所有药品报损末处理的信息
     * 直接访问地址: http://localhost:10000/breakage/list/page=1&limit=20
     * 网关访问地址:http://api.his.com/api/drugwarehouse/breakage/list?page=1&limit=20
     * @return
     */
    @GetMapping("list/{page}/{limit}")
    public ResponseEntity<PageResult<Map>> quertBreakage(
            @PathVariable Integer page,
            @PathVariable Integer limit){
        PageResult<Map> pageResult = sysBreakageService.quertBreakage(page,limit);

        if (pageResult==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return  ResponseEntity.ok(pageResult);
    }

    /**
     * 修改药品报损信息中 审核人、审核意见、是否通过、审核时间
     * 直接访问地址: http://localhost:10000/drugPricing
     * 网关访问地址:http://api.his.com/api/drugwarehouse/breakage
     * @param map
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> putBreakage(@RequestParam Map map){
        Integer dpprocessing = Integer.parseInt(map.get("bprocessing").toString());
        if (dpprocessing==3){
            //只修改药品调价表
            sysBreakageService.updateBreakage(map);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            //修改成功，之后在根据药品入库id，删除药品入库
            sysBreakageService.putBreakage(map);
            return  ResponseEntity.ok().build();
        }
    }

    /**
     * 删除药品报损信息
     * 直接访问地址: http://localhost:10000/drugPricing
     * 网关访问地址:http://api.his.com/api/drugwarehouse/breakage
     * @param bid
     * @return
     */
    @DeleteMapping("del/{bid}")
    public ResponseEntity<Void> delBreakage(@PathVariable String bid){
        sysBreakageService.delBreakage(bid);
        return ResponseEntity.ok().build();
    }
}
