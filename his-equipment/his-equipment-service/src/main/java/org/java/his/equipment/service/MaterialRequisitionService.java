package org.java.his.equipment.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.java.his.enums.ShoppingEnums;
import org.java.his.equipment.dao.MaterialRequisitionMapper;
import org.java.his.equipment.pojo.MaterialRequisition;
import org.java.his.equipment.pojo.MedicalWarehouse;
import org.java.his.exception.ShoppingException;
import org.java.his.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class MaterialRequisitionService {

    @Autowired
    private MedicalWarehouseService medicalWwarehouseService;

    @Autowired
    private MaterialRequisitionMapper materialRequisitionMapper;

    /**
     *添加领料单
     * @param map
     */
    public void addMaterialRequisition(Map map) {

        //生成uuid
        String uuid = UUID.randomUUID().toString();
        //因为UUID本身为32位只是生成时多了“-”，所以将它们去点就可
        uuid = uuid.replace("-", "");

        //获得医疗设备名称
        String itemName=map.get("itemName").toString();

        MedicalWarehouse medicalWarehouse = medicalWwarehouseService.quertByName(itemName);

        //给领料单对象赋值
        MaterialRequisition m=new MaterialRequisition();

        m.setMrId(uuid); //领料单编号
        m.setMid(medicalWarehouse.getMid()); //医疗设备库房编号
        m.setDId(map.get("dId").toString());  //部门编号
        m.setDepartmentsId(map.get("departmentsId").toString());  //科室编号
        m.setRecipientsWay(map.get("recipientsWay").toString());  //领用方式
        m.setNumberRecipients(Integer.parseInt(map.get("numberRecipients").toString()));  //领用数量
        m.setTime(new Date());//领用时间

        int rows = materialRequisitionMapper.insertSelective(m);

        if (rows==0){
            throw new ShoppingException(ShoppingEnums.MATERIAL_ADD_NOT_FOUND);
        }

        medicalWwarehouseService.updateInventoryQuantity(medicalWarehouse.getMid(),
                            Integer.parseInt(map.get("numberRecipients").toString()));
    }

    public PageResult<Map> loadAll(String itemName,String category,Integer page,Integer limit ) {
        //设置pageHelper
        PageHelper.startPage(page,limit);

        //获得所有医疗设备信息
        List<Map> list = materialRequisitionMapper.loadAll(itemName,category);

        //判断查询结果是否为空
        if (CollectionUtils.isEmpty(list)){
            throw new ShoppingException(ShoppingEnums.DRUGWAREHOUSE_LIST_NOT_FOUND);
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
     * 删除领料单
     * @param mrId
     */
    public void delMaterialRequisition(String mrId) {
        int rows = materialRequisitionMapper.deleteByPrimaryKey(mrId);
        if (rows==0){
            throw new ShoppingException(ShoppingEnums.MATERIAL_DEL_NOT_FOUND);
        }
    }
}
