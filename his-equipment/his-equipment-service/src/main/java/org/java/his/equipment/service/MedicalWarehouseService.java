package org.java.his.equipment.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.java.his.enums.ShoppingEnums;
import org.java.his.equipment.dao.MedicalWarehouseMapper;
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
 * 医疗库房
 */
@Service
public class MedicalWarehouseService {

    @Autowired
    private MedicalWarehouseMapper medicalWarehouseMapper;

    public void addMedicalWwarehouse(MedicalWarehouse m){

        String uuid = UUID.randomUUID().toString();
        //因为UUID本身为32位只是生成时多了“-”，所以将它们去点就可
        uuid = uuid.replace("-", "");

        m.setMid(uuid);//医疗库房编号
        m.setTime(new Date());

        int rows = medicalWarehouseMapper.insertSelective(m);

        if (rows==0){
            throw new ShoppingException(ShoppingEnums.MEDICAL_ADD_NOT_FOUND);
        }
    }

    /**
     * 根据设备名称，获得医疗库房详情
     * @param itemName
     * @return
     */
    public MedicalWarehouse quertByName(String itemName){
        //创建查询实例(查询时，可以自己在xml文件中编写 语句查询，也可以使用tkmybatis的方法查询)
        Example example=new Example(MedicalWarehouse.class);

        //封装查询条件(criteria:它可以封装查询条件)
        Example.Criteria criteria = example.createCriteria();

        criteria.andLike("itemName",itemName);

        List<MedicalWarehouse> medicalWarehouses = medicalWarehouseMapper.selectByExample(example);

        return medicalWarehouses.get(0);
    }

    /**
     * 加载所有存在医疗库房的信息,除了退货的设备信息
     * @param category
     * @param itemName
     */
    public PageResult<MedicalWarehouse> loadAll(Integer page,Integer limit,String category, String itemName) {

//        //创建查询实例(查询时，可以自己在xml文件中编写 语句查询，也可以使用tkmybatis的方法查询)
//        Example example=new Example(MedicalWarehouse.class);
//
//        //封装查询条件(criteria:它可以封装查询条件)
//        Example.Criteria criteria = example.createCriteria();
//
//        //组装查询条件
//        if (!StringUtils.isEmpty(category)){
//            criteria.andLike("category","%"+category+"%");
//        }
//        if (!StringUtils.isEmpty(itemName)){
//            criteria.andLike("itemName","%"+itemName+"%");
//        }



        //设置pageHelper
        PageHelper.startPage(page,limit);

        //获得所有医疗设备信息
        List<MedicalWarehouse> list = medicalWarehouseMapper.loadAll(category,itemName);

        //判断查询结果是否为空
        if (CollectionUtils.isEmpty(list)){
            throw new ShoppingException(ShoppingEnums.DRUGWAREHOUSE_LIST_NOT_FOUND);
        }

        //执行分页查询
        PageInfo<MedicalWarehouse> pageInfo = new PageInfo<>(list);

        //将查询的结果，封装成PageResult对象
        PageResult<MedicalWarehouse> pageResult = new PageResult<>();
        pageResult.setCode(0);//请求正常
        pageResult.setCount(pageInfo.getTotal());//数据表中的数据总数
        pageResult.setData(pageInfo.getList());//品牌的集合

        return pageResult;
    }

    /**
     * 据医疗设备编号，删除医疗设备
     * @param mid
     */
    public void delMedical(String mid) {

        int rows = medicalWarehouseMapper.deleteByPrimaryKey(mid);

        if (rows==0){
            throw new ShoppingException(ShoppingEnums.MEDICAL_DEL_NOT_FOUND);
        }
    }

    /**
     * 加载所有存在医疗设备库房的信息,按末付款医疗设备排序,除了退货的设备信息
     * @param page
     * @param limit
     * @param mid
     * @return
     */
    public PageResult<MedicalWarehouse> loadAllOrder(Integer page, Integer limit, String mid) {

        //创建查询实例(查询时，可以自己在xml文件中编写 语句查询，也可以使用tkmybatis的方法查询)
//        Example example=new Example(MedicalWarehouse.class);
//
//        example.setOrderByClause("ifPayment desc");//排序，按末付款的降序排序
//        //example.orderBy("ifPayment");  //排序，从小到大
//
//        //封装查询条件(criteria:它可以封装查询条件)
//        Example.Criteria criteria = example.createCriteria();
//
//
//
//        //组装查询条件
//        if (!StringUtils.isEmpty(mid)){
//            criteria.andLike("mid",mid);
//        }



        //设置pageHelper
        PageHelper.startPage(page,limit);

        //获得所有医疗设备信息
        List<MedicalWarehouse> list = medicalWarehouseMapper.load(mid);

        //判断查询结果是否为空
        if (CollectionUtils.isEmpty(list)){
            throw new ShoppingException(ShoppingEnums.DRUGWAREHOUSE_LIST_NOT_FOUND);
        }

        //执行分页查询
        PageInfo<MedicalWarehouse> pageInfo = new PageInfo<>(list);

        //将查询的结果，封装成PageResult对象
        PageResult<MedicalWarehouse> pageResult = new PageResult<>();
        pageResult.setCode(0);//请求正常
        pageResult.setCount(pageInfo.getTotal());//数据表中的数据总数
        pageResult.setData(pageInfo.getList());//品牌的集合

        return pageResult;
    }

    /**
     * 根据医疗设备库房编号，修改是否付款：为已付款
     * @param mid
     */
    public void updateIfPayment(String mid) {

        int rows = medicalWarehouseMapper.updateIfPayment(mid);

        if (rows==0){
            throw new ShoppingException(ShoppingEnums.MEDICAL_UPDATE_NOT_FOUND);
        }
    }

    /**
     * 退货
     * @param mid
     * @param salesReturn
     */
    public void salesReturn(String mid, String salesReturn) {

        MedicalWarehouse medicalWarehouse=new MedicalWarehouse();

        medicalWarehouse.setMid(mid);
        medicalWarehouse.setSalesReturn(salesReturn);
        medicalWarehouse.setIfPayment(3);
        medicalWarehouse.setReturnTime(new Date());

        int rows = medicalWarehouseMapper.updateByPrimaryKeySelective(medicalWarehouse);
        if (rows==0){
            throw new ShoppingException(ShoppingEnums.MEDICAL_UPDATE_NOT_FOUND);
        }
    }

    /**
     *加载所有已退货的设备信息
     */
    public PageResult<MedicalWarehouse> loadSalesReturn(String mid,Integer page,Integer limit) {
        //创建查询实例(查询时，可以自己在xml文件中编写 语句查询，也可以使用tkmybatis的方法查询)
        Example example=new Example(MedicalWarehouse.class);

//        example.setOrderByClause("ifPayment desc");//排序，按末付款的降序排序
        //example.orderBy("ifPayment");  //排序，从小到大

        //封装查询条件(criteria:它可以封装查询条件)
        Example.Criteria criteria = example.createCriteria();



        //组装查询条件
        if (!StringUtils.isEmpty(mid)){
            criteria.andLike("mid",mid);
        }

        criteria.andLike("ifPayment","3");


        //设置pageHelper
        PageHelper.startPage(page,limit);

        //获得所有医疗设备信息
        List<MedicalWarehouse> list = medicalWarehouseMapper.selectByExample(example);

        //判断查询结果是否为空
        if (CollectionUtils.isEmpty(list)){
            throw new ShoppingException(ShoppingEnums.DRUGWAREHOUSE_LIST_NOT_FOUND);
        }

        //执行分页查询
        PageInfo<MedicalWarehouse> pageInfo = new PageInfo<>(list);

        //将查询的结果，封装成PageResult对象
        PageResult<MedicalWarehouse> pageResult = new PageResult<>();
        pageResult.setCode(0);//请求正常
        pageResult.setCount(pageInfo.getTotal());//数据表中的数据总数
        pageResult.setData(pageInfo.getList());//品牌的集合

        return pageResult;
    }
}
