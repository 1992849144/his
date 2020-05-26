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

        int rows = logisticsWarehouseMapper.insertSelective(logisticsWarehouse);

        if (rows==0){
            throw new ShoppingException(ShoppingEnums.LOGISTICS_ADD_NOT_FOUND);
        }
    }


    public PageResult<LogisticsWarehouse> loadAll(String itemName,Integer page,Integer limit) {

                //创建查询实例(查询时，可以自己在xml文件中编写 语句查询，也可以使用tkmybatis的方法查询)
        Example example=new Example(LogisticsWarehouse.class);

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
}
