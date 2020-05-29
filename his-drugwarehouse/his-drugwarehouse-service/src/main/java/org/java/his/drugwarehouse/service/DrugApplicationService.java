package org.java.his.drugwarehouse.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.java.his.drugwarehouse.mapper.DrugApplicationMapper;
import org.java.his.drugwarehouse.pojo.DrugApplication;
import org.java.his.drugwarehouse.pojo.Drugwarehouse;
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
import java.util.UUID;

/**
 * 药品申请
 */
@Service
public class DrugApplicationService {

    @Autowired
    private DrugApplicationMapper drugApplicationMapper;

    public void addDrugApplication(DrugApplication drugApplication){

        //生成uuid
        String uuid = UUID.randomUUID().toString();
        //因为UUID本身为32位只是生成时多了“-”，所以将它们去点就可
        uuid = uuid.replace("-", "");

        //赋值编号
        drugApplication.setDaId(uuid);
        drugApplication.setTimeApplication(new Date());
        drugApplication.setWhetherPurchase(2);

        int rows = drugApplicationMapper.insertSelective(drugApplication);

        if (rows==0){
            throw new ShoppingException(ShoppingEnums.DRUGAPPLICATION_ADD_NOT_FOUND);
        }
    }

    public PageResult<DrugApplication> loadAll(Integer page, Integer limit,String daId,String drugCatalogueId) {

        //创建查询实例(查询时，可以自己在xml文件中编写 语句查询，也可以使用tkmybatis的方法查询)
        Example example=new Example(DrugApplication.class);

        example.setOrderByClause("whetherPurchase desc");//排序，按末付款的降序排序

        //封装查询条件(criteria:它可以封装查询条件)
        Example.Criteria criteria = example.createCriteria();

        //组装查询条件
        if (!StringUtils.isEmpty(daId)){
            criteria.andLike("daId","%"+daId+"%");
        }
        if (!StringUtils.isEmpty(drugCatalogueId)){
            criteria.andLike("drugCatalogueId","%"+drugCatalogueId+"%");
        }

        //设置pageHelper
        PageHelper.startPage(page,limit);

        //获得所有药品信息
        List<DrugApplication> list = drugApplicationMapper.selectByExample(example);

        //判断查询结果是否为空
        if (CollectionUtils.isEmpty(list)){
            throw new ShoppingException(ShoppingEnums.DRUGWAREHOUSE_LIST_NOT_FOUND);
        }

        //执行分页查询
        PageInfo<DrugApplication> pageInfo = new PageInfo<>(list);

        //将查询的结果，封装成PageResult对象
        PageResult<DrugApplication> pageResult = new PageResult<>();
        pageResult.setCode(0);//请求正常
        pageResult.setCount(pageInfo.getTotal());//数据表中的数据总数
        pageResult.setData(pageInfo.getList());//品牌的集合

        return pageResult;
    }

    /**
     * 删除药品申请
     * @param daId
     */
    public void delDrugApplication(String daId) {

        int rows = drugApplicationMapper.deleteByPrimaryKey(daId);

        if (rows==0){
            throw new ShoppingException(ShoppingEnums.DRUGAPPLICATION_DEL_NOT_FOUND);
        }
    }

    /**
     *  修改药品申请
     * @param drugApplication
     */
    public void updateDrugApplication(DrugApplication drugApplication) {
        drugApplication.setTimeApplication(new Date());
        drugApplication.setWhetherPurchase(2);
        drugApplicationMapper.updateByPrimaryKey(drugApplication);
    }

    /**
     * 采购药品
     * @param drugApplication
     */
    public void purchase(DrugApplication drugApplication){
        drugApplication.setTime(new Date());

        drugApplicationMapper.purchase(drugApplication);
    }
}
