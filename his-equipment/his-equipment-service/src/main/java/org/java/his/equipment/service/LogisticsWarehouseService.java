package org.java.his.equipment.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.java.his.enums.ShoppingEnums;
import org.java.his.equipment.dao.LogisticsWarehouseMapper;
import org.java.his.equipment.pojo.LogisticsWarehouse;
import org.java.his.equipment.pojo.MedicalWarehouse;
import org.java.his.exception.ShoppingException;
import org.java.his.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 后勤库房
 */
@Service
public class LogisticsWarehouseService {

    @Autowired
    private LogisticsWarehouseMapper logisticsWarehouseMapper;

    /**
     * 后勤库房添加
     * @param logisticsWarehouse
     */
    public void addLogistics(LogisticsWarehouse logisticsWarehouse) {

        String uuid = UUID.randomUUID().toString();
        //因为UUID本身为32位只是生成时多了“-”，所以将它们去点就可
        uuid = uuid.replace("-", "");

        logisticsWarehouse.setLogisticsId(uuid);
        logisticsWarehouse.setTime(new Date());

        if(logisticsWarehouse.getSelfControl()==1){
            logisticsWarehouse.setIfPayment(null);
        }

        int rows = logisticsWarehouseMapper.insertSelective(logisticsWarehouse);

        if (rows==0){
            throw new ShoppingException(ShoppingEnums.LOGISTICS_ADD_NOT_FOUND);
        }
    }

    /**
     * 显示所有后勤库房
     * @param itemName
     * @param page
     * @param limit
     * @return
     */
    public PageResult<LogisticsWarehouse> loadAll(String itemName,Integer page,Integer limit) {

        //创建查询实例(查询时，可以自己在xml文件中编写 语句查询，也可以使用tkmybatis的方法查询)
        Example example=new Example(LogisticsWarehouse.class);

        example.setOrderByClause("time desc");//排序，按末付款的降序排序

        //封装查询条件(criteria:它可以封装查询条件)
        Example.Criteria criteria = example.createCriteria();

        //组装查询条件
        if (!StringUtils.isEmpty(itemName)){
            criteria.andLike("itemNames","%"+itemName+"%");
        }


        //设置pageHelper
        PageHelper.startPage(page,limit);

        //获得所有医疗设备信息
        List<LogisticsWarehouse> list = logisticsWarehouseMapper.selectByExample(example);

        //判断查询结果是否为空
        if (CollectionUtils.isEmpty(list)){
            throw new ShoppingException(ShoppingEnums.DRUGWAREHOUSE_LIST_NOT_FOUND);
        }

        //执行分页查询
        PageInfo<LogisticsWarehouse> pageInfo = new PageInfo<>(list);

        //将查询的结果，封装成PageResult对象
        PageResult<LogisticsWarehouse> pageResult = new PageResult<>();
        pageResult.setCode(0);//请求正常
        pageResult.setCount(pageInfo.getTotal());//数据表中的数据总数
        pageResult.setData(pageInfo.getList());//品牌的集合

        return pageResult;
    }

    /**
     * 根据库房编号删除
     * @param logisticsId
     */
    public void delLogistics(String logisticsId) {

        int rows = logisticsWarehouseMapper.deleteByPrimaryKey(logisticsId);

        if (rows==0){
            throw new ShoppingException(ShoppingEnums.LOGISTICS_DEL_NOT_FOUND);
        }
    }

    /**
     * 根据名称，获得后勤库房详情
     * @param itemNames
     * @return
     */
    public LogisticsWarehouse quertByName(String itemNames){
        Example example=new Example(LogisticsWarehouse.class);

        //封装查询条件(criteria:它可以封装查询条件)
        Example.Criteria criteria = example.createCriteria();

        //组装查询条件
        if (StringUtils.isEmpty(itemNames)){
            return null;
        }

        criteria.andLike("itemNames",itemNames);
        List<LogisticsWarehouse> list = logisticsWarehouseMapper.selectByExample(example);

        return list.get(0);
    }

    /**
     * 修改库存数量，根据库存数量-领用数量
     * @param logisticsId
     * @param numberRecipients
     */
    public void updateCount(Integer numberRecipients,String logisticsId){
        logisticsWarehouseMapper.updateCount(logisticsId,numberRecipients);
    }

    /**
     * 修改医疗设备库房中的数量
     * @param inventoryQuantity
     * @param logisticsId
     */
    public void updateInventoryQuantity(Integer inventoryQuantity, String logisticsId) {
        logisticsWarehouseMapper.updateInventoryQuantity(inventoryQuantity,logisticsId);
    }

    /**
     * 显示所有付款登记，按照末付款进行排序
     * @param itemName
     * @param page
     * @param limit
     * @return
     */
    public PageResult<LogisticsWarehouse> order(String itemName, Integer page, Integer limit) {
        //创建查询实例(查询时，可以自己在xml文件中编写 语句查询，也可以使用tkmybatis的方法查询)
        Example example=new Example(LogisticsWarehouse.class);

        example.setOrderByClause("ifPayment desc");//排序，按末付款的降序排序

        //封装查询条件(criteria:它可以封装查询条件)
        Example.Criteria criteria = example.createCriteria();

        //组装查询条件
        if (!StringUtils.isEmpty(itemName)){
            criteria.andLike("itemNames","%"+itemName+"%");
        }

        criteria.andLike("selfControl","2");

        //设置pageHelper
        PageHelper.startPage(page,limit);

        //获得所有医疗设备信息
        List<LogisticsWarehouse> list = logisticsWarehouseMapper.selectByExample(example);

        //判断查询结果是否为空
        if (CollectionUtils.isEmpty(list)){
            throw new ShoppingException(ShoppingEnums.DRUGWAREHOUSE_LIST_NOT_FOUND);
        }

        //执行分页查询
        PageInfo<LogisticsWarehouse> pageInfo = new PageInfo<>(list);

        //将查询的结果，封装成PageResult对象
        PageResult<LogisticsWarehouse> pageResult = new PageResult<>();
        pageResult.setCode(0);//请求正常
        pageResult.setCount(pageInfo.getTotal());//数据表中的数据总数
        pageResult.setData(pageInfo.getList());//品牌的集合

        return pageResult;
    }

    /**
     * 付款
     * @param logisticsId
     */
    public void payment(String logisticsId) {
        logisticsWarehouseMapper.payment(logisticsId);
    }
}
