package org.java.his.drugwarehouse.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.java.auth.pojo.UserInfo;
import org.java.his.drugwarehouse.interceptor.DrugItercetpor;
import org.java.his.drugwarehouse.mapper.SysDrugPricingMapper;
import org.java.his.drugwarehouse.pojo.Drugwarehouse;
import org.java.his.drugwarehouse.pojo.SysDrugPricing;
import org.java.his.enums.ShoppingEnums;
import org.java.his.exception.ShoppingException;
import org.java.his.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 药品调价
 */
@Service
public class SysDrugPricingService {

    @Autowired
    private SysDrugPricingMapper sysDrugPricingMapper;

    @Autowired
    private DrugwarehouseService drugwarehouseService;

    /**
     * 把药品调价信息，添加到药品调价表中，要库房主管同意之后，才对药品做出调价
     * @param map
     */
    public void addSysDrugPricing(Map map){
        //创建药品调价对象
        SysDrugPricing sysDrugPricing=new SysDrugPricing();

        //获得uuid
        String uuid = UUID.randomUUID().toString();
        //因为UUID本身为32位只是生成时多了“-”，所以将它们去点就可
        uuid = uuid.replace("-", "");

        //给药品调价对象赋值
        sysDrugPricing.setDpid(uuid);
        sysDrugPricing.setId(map.get("id").toString());
        sysDrugPricing.setDpprotretailprice(Double.parseDouble(map.get("protretailprice").toString()));
        sysDrugPricing.setDpnowretailprice(Double.parseDouble(map.get("nowretailprice").toString()));
        sysDrugPricing.setDuereasons(map.get("duereasons").toString());
        sysDrugPricing.setDpadjusttime(new Date());
        sysDrugPricing.setDpprocessing(1);
        sysDrugPricing.setDpoperator("jack");

        //添加
        int rows = sysDrugPricingMapper.insertSelective(sysDrugPricing);

        if (rows==0){
            throw new ShoppingException(ShoppingEnums.SYSDRUGPRICING_ADD_FALLURE);
        }
    }

    /**
     * 查询所有药品调价末处理的信息
     * @return
     */
    public PageResult<Map> quertDrugPricing(Integer page,Integer limit){

        //获得用户信息
        UserInfo userinfo = DrugItercetpor.getUserinfo();

        if (userinfo.getName().equals("库房主管")){
            //设置pageHelper
            PageHelper.startPage(page,limit);

            List<Map> list = sysDrugPricingMapper.quertDrugPricing();

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
     * 查询药品调价信息，根据药品编号,不连药品入库表
     * @param id
     */
    public List<SysDrugPricing> queryDrugPricingByid(String id){
        return  sysDrugPricingMapper.queryDrugPricingByid(id);
    }

    /**
     * 查询药品调价信息，根据药品编号,连药品入库表
     * @param dpid
     */
    public Map queryDrugPricingByDpid(String dpid){
        return  sysDrugPricingMapper.queryDrugPricingByDpid(dpid);
    }

    /**
     * 修改药品调价信息中 审核人、审核意见、是否通过、审核时间，加药品入库表
     * @param map
     * @return
     */
    public void putDrugPricing(Map map) {

        //修改药品入库表
        drugwarehouseService.putPriceadjustment(map);

        //修改药品调价表
        UserInfo userinfo = DrugItercetpor.getUserinfo(); //从线程变量中获得用户名
        map.put("dpassessor",userinfo.getUsername()); //获得审核时间
        map.put("audittime",new Date());

        int rows = sysDrugPricingMapper.putDrugPricing(map);

        if (rows==0){
            throw new ShoppingException(ShoppingEnums.SYSDRUGPRICING_UPDATE_FALLURE);
        }
    }

    /**
     * 修改药品调价信息中 审核人、审核意见、是否通过、审核时间，不加药品入库表
     * @param map
     * @return
     */
    public void updateDrugPricing(Map map) {

        //修改药品调价表
        UserInfo userinfo = DrugItercetpor.getUserinfo(); //从线程变量中获得用户名
        map.put("dpassessor",userinfo.getUsername()); //获得审核时间
        map.put("audittime",new Date());

        int rows = sysDrugPricingMapper.updateDrugPricing(map);

        if (rows==0){
            throw new ShoppingException(ShoppingEnums.SYSDRUGPRICING_UPDATE_FALLURE);
        }
    }

    /**
     * 查询除了末处理的药品调价的信息
     */
    public PageResult<Map> queryAll(Integer page,Integer limit, String drugname, String supplier) {

        //设置pageHelper
        PageHelper.startPage(page,limit);


        List<Map> list = sysDrugPricingMapper.queryAll(drugname,supplier);

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

        //将当前页，与 总页数，设置到PageResult对象，返回到前台，用于分页
        pageResult.setPageNum(pageInfo.getPageNum());//当前页
        pageResult.setMaxPage(pageInfo.getPages());//最大页

        return pageResult;
    }

    /**
     * 删除药品调价信息
     * @param id
     */
    public void delDrugPricing(String id) {
        int rows = sysDrugPricingMapper.deleteByPrimaryKey(id);
        if (rows==0){
            throw new ShoppingException(ShoppingEnums.SYSDRUGPRICING_DEL_FALLURE);
        }
    }
}
