package org.java.his.drugwarehouse.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.java.his.drugwarehouse.mapper.DrugwarehouseMapper;
import org.java.his.drugwarehouse.mapper.SysBreakageMapper;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 药品入库
 */
@Service
public class DrugwarehouseService {

    @Autowired
    private DrugwarehouseMapper drugwarehouseMapper;

    @Autowired
    private SysDrugPricingService sysDrugPricingService;

    @Autowired
    private SysDrugPricingMapper sysDrugPricingMapper;

    /**
     * 添加药品入库
     * @param drugwarehouse
     */
    public void savaDrugwarehouse(Drugwarehouse drugwarehouse) {
        //生成uuid
        String uuid = UUID.randomUUID().toString();
        //因为UUID本身为32位只是生成时多了“-”，所以将它们去点就可
        uuid = uuid.replace("-", "");

        //给id赋值
        drugwarehouse.setId(uuid);
        drugwarehouse.setIfdelete(2);

        //添加
        int rows = drugwarehouseMapper.insertSelective(drugwarehouse);

        if (rows==0){
            throw new ShoppingException(ShoppingEnums.DRUGWAREHOUSE_ADD_NOT_FOUND);
        }
    }

    /**
     * 分页查询药品信息
     */
    public PageResult<Drugwarehouse> loadSku(Integer page, Integer limit, String drugname, String supplier) {

        //创建查询实例(查询时，可以自己在xml文件中编写 语句查询，也可以使用tkmybatis的方法查询)
        Example example=new Example(Drugwarehouse.class);

        //封装查询条件(criteria:它可以封装查询条件)
        Example.Criteria criteria = example.createCriteria();

        //组装查询条件
        if (!StringUtils.isEmpty(drugname)){
            criteria.andLike("drugname","%"+drugname+"%");
        }
        if (!StringUtils.isEmpty(supplier)){
            criteria.andLike("supplier","%"+supplier+"%");
        }
        criteria.andLike("ifdelete","2");
        //设置pageHelper
        PageHelper.startPage(page,limit);

        //获得所有药品信息
        List<Drugwarehouse> list = drugwarehouseMapper.selectByExample(example);

        //判断查询结果是否为空
        if (CollectionUtils.isEmpty(list)){
            throw new ShoppingException(ShoppingEnums.DRUGWAREHOUSE_LIST_NOT_FOUND);
        }

        //执行分页查询
        PageInfo<Drugwarehouse> pageInfo = new PageInfo<>(list);

        //将查询的结果，封装成PageResult对象
        PageResult<Drugwarehouse> pageResult = new PageResult<>();
        pageResult.setCode(0);//请求正常
        pageResult.setCount(pageInfo.getTotal());//数据表中的数据总数
        pageResult.setData(pageInfo.getList());//品牌的集合

        return pageResult;
    }

    /**
     * 删除药品,为了药品报损的时候可以查看，只能修改ifdelete为1了
     * @param id
     */
    public Boolean delDrugwarehouse(String id) {

        //先查询药品调价和药品报损里面，该药品是否在里面有业务

        List<SysDrugPricing> sysDrugPricings = sysDrugPricingMapper.queryDrugPricingByids(id);

        if (!CollectionUtils.isEmpty(sysDrugPricings)){
            return false;
        }

        //删除药库里面的信息
        int rows= drugwarehouseMapper.delDrugwarehouse(id);
        if (rows==0){
            throw new ShoppingException(ShoppingEnums.DRUGWAREHOUSE_DELETE_FAILURE);
        }
        return rows==1;
    }

    /**
     * 根据id，获得药品详情
     * @param id
     * @return
     */
    public Drugwarehouse findDrugwarehouseId(String id){

        Drugwarehouse drugwarehouse = drugwarehouseMapper.selectByPrimaryKey(id);
        return drugwarehouse;
    }

    /**
     * 修改药品
     * @param drugwarehouse
     */
    public void updateDrugwarehouse(Drugwarehouse drugwarehouse){
        drugwarehouse.setIfdelete(2);
        int rows = drugwarehouseMapper.updateByPrimaryKey(drugwarehouse);
        if (rows==0){
            throw new ShoppingException(ShoppingEnums.DRUGWAREHOUSE_UPDATE_FAILURE);
        }
    }

    /**
     * 添加入库数量
     * @param drugwarehouse
     */
    public void putReceipt(Drugwarehouse drugwarehouse) {
        int rows = drugwarehouseMapper.putReceipt(drugwarehouse);
        if (rows==0){
            throw new ShoppingException(ShoppingEnums.DRUGWAREHOUSE_PUTRECEIPT_FAILURE);
        }
    }

    /**
     * 药品调价
     * @param map
     */
    public void putPriceadjustment(Map map) {

        Map map1=new HashMap();
        //从map中获得编号、批发价、现零售价
        String id = map.get("id").toString();
        String retailprice = map.get("dpprotretailprice").toString();
        String wholesaleprice =map.get("dpnowretailprice").toString();


        map1.put("id",id);
        map1.put("retailprice",retailprice);
        map1.put("wholesaleprice",wholesaleprice);

        //药品调价
        int rows = drugwarehouseMapper.putPriceadjustment(map1);
        if (rows==0){
            throw new ShoppingException(ShoppingEnums.DRUGWAREHOUSE_PUUPRICEADJUSTMENT_FAILURE);
        }
    }

    /**
     * 根据药品名称，获得药品详情
     * @param drugname
     * @return
     */
    public Drugwarehouse queryDrugName(String drugname){
        //创建查询实例(查询时，可以自己在xml文件中编写 语句查询，也可以使用tkmybatis的方法查询)
        Example example=new Example(Drugwarehouse.class);

        //封装查询条件(criteria:它可以封装查询条件)
        Example.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(drugname)){
            criteria.andLike("drugname",drugname);
        }

        List<Drugwarehouse> drugwarehouses = drugwarehouseMapper.selectByExample(example);

        for (Drugwarehouse d:drugwarehouses){
            return d;
        }
        return null;
    }

    /**
     * 修改入库量为0
     * @param id
     */
    public void updateReceiptZero(String id){
        drugwarehouseMapper.updateReceiptZero(id);
    }

    /**
     * 库存减去出库量,修改库存
     * @param id
     * @param receipt
     */
    public void updateCount(String id,Integer receipt){
        drugwarehouseMapper.updateCount(id,receipt);
    }
}
