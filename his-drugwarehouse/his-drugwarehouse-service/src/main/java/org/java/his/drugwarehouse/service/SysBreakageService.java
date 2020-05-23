package org.java.his.drugwarehouse.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.java.auth.pojo.UserInfo;
import org.java.his.drugwarehouse.interceptor.DrugItercetpor;
import org.java.his.drugwarehouse.mapper.SysBreakageMapper;
import org.java.his.drugwarehouse.pojo.SysBreakage;
import org.java.his.enums.ShoppingEnums;
import org.java.his.exception.ShoppingException;
import org.java.his.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 药品报损
 */
@Service
public class SysBreakageService {

    @Autowired
    private SysBreakageMapper sysBreakageMapper;

    @Autowired
    private DrugwarehouseService drugwarehouseService;

    /**
     * 添加到药品报损表
     * @param map
     */
    public void addBreakage(Map map){

        //创建药品报损对象
        SysBreakage sysBreakage=new SysBreakage();

        //获得uuid
        String uuid = UUID.randomUUID().toString();
        //因为UUID本身为32位只是生成时多了“-”，所以将它们去点就可
        uuid = uuid.replace("-", "");

        //给药品报损对象赋值
        sysBreakage.setBid(uuid);
        sysBreakage.setId(map.get("id").toString());
        sysBreakage.setBnumberreported(Integer.parseInt(map.get("numberreported").toString()));
        sysBreakage.setBzerocoefficient(Double.parseDouble(map.get("zerocoefficient").toString()));
        sysBreakage.setReportedcauses(map.get("reportedcauses").toString());
        sysBreakage.setBadjusttime(new Date());
        sysBreakage.setBprocessing(1);
        sysBreakage.setBoperator("jack");

        //添加
        int rows = sysBreakageMapper.insertSelective(sysBreakage);

        if (rows==0){
            throw new ShoppingException(ShoppingEnums.SYSDRUGPRICING_ADD_FALLURE);
        }
    }

    /**
     * 查询所有药品报损末处理的信息
     * @param page
     * @param limit
     * @return
     */
    public PageResult<Map> quertBreakage(Integer page, Integer limit) {

        //获得用户信息
        UserInfo userinfo = DrugItercetpor.getUserinfo();

        if (userinfo.getName().equals("库房主管")){
            //设置pageHelper
            PageHelper.startPage(page,limit);

            List<Map> list = sysBreakageMapper.quertBreakage();

            //判断查询结果是否为空
            if (CollectionUtils.isEmpty(list)){
                throw new ShoppingException(ShoppingEnums.SYSDRUGPRICING_LIST_FALLURE);
            }
            //执行分页查询
            PageInfo<Map> pageInfo = new PageInfo<>(list);

            //将查询的结果，封装成PageResult对象
            PageResult<Map> pageResult = new PageResult<>();
            pageResult.setCode(0);//请求正常
            pageResult.setCount(pageInfo.getTotal());//数据表中的数据总数
            pageResult.setData(pageInfo.getList());//品牌的集合

            //将当前页，与 总页数，设置到PageResult对象，返回到前台，用于分页
            pageResult.setPageNum(pageInfo.getPageNum());//当前页
            pageResult.setMaxPage(pageInfo.getPages());//最大页

            return pageResult;
        }
        return null;
    }

    /**
     * 查询药品报损信息，根据药品编号,连药品入库表
     * @param bid
     */
    public Map queryBreakage(String bid) {
        return  sysBreakageMapper.quertBreakageByBid(bid);
    }

    /**
     * 修改药品调价信息中 审核人、审核意见、是否通过、审核时间，不加药品入库表
     * @param map
     */
    public void updateBreakage(Map map) {

        //修改药品调价表
        UserInfo userinfo = DrugItercetpor.getUserinfo(); //从线程变量中获得用户名
        map.put("bassessor",userinfo.getUsername()); //获得审核人
        map.put("baudittime",new Date());

        int rows = sysBreakageMapper.updateBreakage(map);

        if (rows==0){
            throw new ShoppingException(ShoppingEnums.SYSBREAKAGE_ADD_FALLURE);
        }

    }

    /**
     * 修改药品调价信息中 审核人、审核意见、是否通过、审核时间，加药品入库表
     * @param map
     */
    public void putBreakage(Map map) {

        //删除药品入库表
        drugwarehouseService.delDrugwarehouse(map.get("id").toString());

        //修改药品调价表
        UserInfo userinfo = DrugItercetpor.getUserinfo(); //从线程变量中获得用户名
        map.put("bassessor",userinfo.getUsername()); //获得审核时间
        map.put("baudittime",new Date());

        int rows = sysBreakageMapper.putDrugPricing(map);

        if (rows==0){
            throw new ShoppingException(ShoppingEnums.SYSBREAKAGE_ADD_FALLURE);
        }
    }

    /**
     * 查询除了末处理的药品报损的信息
     */
    public PageResult<Map> queryAllBreakage(Integer page, Integer limit, String drugname, String supplier) {

        //设置pageHelper
        PageHelper.startPage(page,limit);


        List<Map> list = sysBreakageMapper.queryAllBreakage(drugname,supplier);

        if (CollectionUtils.isEmpty(list)){
            throw new ShoppingException(ShoppingEnums.SYSDRUGPRICING_LISTALL_FALLURE);
        }

        //执行分页查询
        PageInfo<Map> pageInfo = new PageInfo<>(list);

        //将查询的结果，封装成PageResult对象
        PageResult<Map> pageResult = new PageResult<>();
        pageResult.setCode(0);//请求正常
        pageResult.setCount(pageInfo.getTotal());//数据表中的数据总数
        pageResult.setData(pageInfo.getList());//品牌的集合

        return pageResult;
    }

    /**
     * 删除药品报损信息
     * @param bid
     */
    public void delBreakage(String bid) {
        int rows = sysBreakageMapper.deleteByPrimaryKey(bid);
        if (rows==0){
            throw new ShoppingException(ShoppingEnums.BREAKAGE_DEL_FALLURE);
        }
    }
}