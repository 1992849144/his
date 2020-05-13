package org.java.his.drugwarehouse.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.java.his.drugwarehouse.mapper.DrugwarehouseMapper;
import org.java.his.drugwarehouse.pojo.Drugwarehouse;
import org.java.shopping.enums.HisEnums;
import org.java.shopping.exception.HisException;
import org.java.shopping.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
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
            throw new HisException(HisEnums.DRUGWAREHOUSE_ADD_NOT_FOUND);
        }
    }

    /**
     * 分页查询药品信息
     */
    public PageResult<Drugwarehouse> loadSku(Integer page, Integer limit) {
        //设置pageHelper
        PageHelper.startPage(page,limit);

        //获得所有药品信息
        List<Drugwarehouse> list = drugwarehouseMapper.selectAll();

        //判断查询结果是否为空
        if (CollectionUtils.isEmpty(list)){
            throw new HisException(HisEnums.DRUGWAREHOUSE_LIST_NOT_FOUND);
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
            throw new HisException(HisEnums.DRUGWAREHOUSE_DELETE_FAILURE);
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
            throw new HisException(HisEnums.DRUGWAREHOUSE_ADD_FAILURE);
        }
    }
}
