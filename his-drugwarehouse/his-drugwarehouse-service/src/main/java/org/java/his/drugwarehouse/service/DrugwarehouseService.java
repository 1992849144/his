package org.java.his.drugwarehouse.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.java.his.drugwarehouse.mapper.DrugwarehouseMapper;
import org.java.his.drugwarehouse.pojo.Drugwarehouse;

import org.java.his.enums.ShoppingEnums;
import org.java.his.exception.ShoppingException;
import org.java.his.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class DrugwarehouseService {

    @Autowired
    private DrugwarehouseMapper drugwarehouseMapper;

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

        //将当前页，与 总页数，设置到PageResult对象，返回到前台，用于分页
        pageResult.setPageNum(pageInfo.getPageNum());//当前页
        pageResult.setMaxPage(pageInfo.getPages());//最大页

        return pageResult;
    }

    /**
     * 删除药品
     * @param id
     */
    public void delDrugwarehouse(String id) {

        int rows= drugwarehouseMapper.deleteByPrimaryKey(id);
        if (rows==0){
            throw new ShoppingException(ShoppingEnums.DRUGWAREHOUSE_DELETE_FAILURE);
        }
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
        int rows = drugwarehouseMapper.updateByPrimaryKey(drugwarehouse);
        if (rows==0){
            throw new ShoppingException(ShoppingEnums.DRUGWAREHOUSE_UPDATE_FAILURE);
        }
    }

    /**
     * 修改入库数量
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
        
        //从map中获得编号、批发价、现零售价
        String id = map.get("id").toString();
        String retailprice = map.get("protretailprice").toString();
        String wholesaleprice =map.get("nowretailprice").toString();

        //药品调价
        int rows = drugwarehouseMapper.putPriceadjustment(map);
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
}
