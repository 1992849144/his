package org.java.his.drugwarehouse.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.java.his.drugwarehouse.mapper.MedicineWarehouseOutServiceMapper;
import org.java.his.drugwarehouse.pojo.Drugwarehouse;
import org.java.his.drugwarehouse.pojo.MedicineWarehouseOut;
import org.java.his.enums.ShoppingEnums;
import org.java.his.exception.ShoppingException;
import org.java.his.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 药品入库
 */
@Service
public class MedicineWarehouseOutService {

    @Autowired
    private MedicineWarehouseOutServiceMapper medicineWarehouseOutServiceMapper;

    @Autowired
    private DrugwarehouseService drugwarehouseService;

    public void addMedicineWarehouseOut(Map map) {

        //生成uuid
        String uuid = UUID.randomUUID().toString();
        //因为UUID本身为32位只是生成时多了“-”，所以将它们去点就可
        uuid = uuid.replace("-", "");

        //获得药品名称
        String drugname = map.get("drugname").toString();
        //获得申请数量
        Integer dwnumberOfApplications = Integer.parseInt(map.get("dwnumberOfApplications").toString());

        //根据药品名称，获得药品详情
        Drugwarehouse drugwarehouse = drugwarehouseService.queryDrugName(drugname);

        //获得药库中的库存数量
        Integer receipt = drugwarehouse.getReceipt();

        MedicineWarehouseOut m = new MedicineWarehouseOut();

        //给MedicineWarehouseOut赋值
        m.setMwId(uuid);  //药品出库编号
        m.setDrugCatalogueId(drugwarehouse.getId()); //药品编号
        m.setIssueDate(new Date()); //出库日期
        m.setDwnumberOfApplications(dwnumberOfApplications); //申请数量
        m.setCount(drugwarehouse.getReceipt()); //库存数量
        m.setPersonReceivingTheMedicine(map.get("personReceivingTheMedicine").toString());//领药人
        m.setOperator(map.get("operator").toString()); //操作人


        if (receipt >= dwnumberOfApplications) {
            //如果库存数量大于申请数量，需要多少就发多少
            m.setDeliveryQuantity(dwnumberOfApplications);
            m.setQuantity(dwnumberOfApplications);
            drugwarehouseService.updateCount(drugwarehouse.getId(),dwnumberOfApplications);
        } else {
            //如果库存数量小于申请数量，就有多少发多少
            m.setDeliveryQuantity(receipt);
            m.setQuantity(receipt);
            drugwarehouseService.updateReceiptZero(drugwarehouse.getId());
        }

        int rows = medicineWarehouseOutServiceMapper.insertSelective(m);
        if (rows==0){
            throw new ShoppingException(ShoppingEnums.MEDICINE_ADD_NOT_FOUND);
        }
    }

    /**
     *加载所有出库信息
     * @param page
     * @param limit
     * @param drugname
     * @param supplier
     * @return
     */
    public PageResult<Map> loadMedicineWarehouseOute(Integer page, Integer limit, String drugname, String supplier) {

        //设置pageHelper
        PageHelper.startPage(page,limit);

        //获得所有药品信息
        List<Map> list = medicineWarehouseOutServiceMapper.loadMedicineWarehouseOute(drugname,supplier);

        //判断查询结果是否为空
        if (CollectionUtils.isEmpty(list)){
            throw new ShoppingException(ShoppingEnums.MEDICINE_LIST_NOT_FOUND);
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
     * 根据出库编号，删除出库信息
     * @param mwId
     */
    public void delMedicineWarehouseOute(String mwId) {
        int rows = medicineWarehouseOutServiceMapper.deleteByPrimaryKey(mwId);
        if (rows==0){
            throw new ShoppingException(ShoppingEnums.MEDICINE_DEL_NOT_FOUND);
        }
    }

    /**
     * 据出库编号,获得出库详情
     * @param mwId
     * @return
     */
    public Map queryMedicineWarehouseOute(String mwId){
        return medicineWarehouseOutServiceMapper.queryMedicineWarehouseOute(mwId);
    }
 }
