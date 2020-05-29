package org.java.his.equipment.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.java.his.enums.ShoppingEnums;
import org.java.his.equipment.dao.LogisticsMaterialMapper;
import org.java.his.equipment.pojo.LogisticsMaterial;
import org.java.his.equipment.pojo.LogisticsWarehouse;
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
 * 后勤库房领料单
 */
@Service
public class LogisticsMaterialService {

    @Autowired
    private LogisticsMaterialMapper logisticsMaterialMapper;

    @Autowired
    private LogisticsWarehouseService logisticsWarehouseService;

    public void addLogisticsMaterial(Map map) {

        String uuid = UUID.randomUUID().toString();
        //因为UUID本身为32位只是生成时多了“-”，所以将它们去点就可
        uuid = uuid.replace("-", "");

        String itemNames = map.get("itemNames").toString();//获得后勤库房的名称

        LogisticsWarehouse lw = logisticsWarehouseService.quertByName(itemNames);

        LogisticsMaterial logisticsMaterial=new LogisticsMaterial();

        logisticsMaterial.setLmId(uuid);
        logisticsMaterial.setLogisticsId(lw.getLogisticsId());
        logisticsMaterial.setDId(map.get("dId").toString());
        logisticsMaterial.setDepartmentsId(map.get("departmentsId").toString());
        logisticsMaterial.setRecipientsWay(map.get("recipientsWay").toString());
        logisticsMaterial.setNumberRecipients(Integer.parseInt(map.get("numberRecipients").toString()));
        logisticsMaterial.setTime(new Date());

        int rows = logisticsMaterialMapper.insertSelective(logisticsMaterial);

        if (rows==0){
            throw new ShoppingException(ShoppingEnums.LMATERIAL_ADD_NOT_FOUND);
        }

        logisticsWarehouseService.updateCount(Integer.parseInt(map.get("numberRecipients").toString()),lw.getLogisticsId());
    }

    /**
     * 显示所有领料单
     * @param itemName
     * @param page
     * @param limit
     * @return
     */
    public PageResult<Map> loadAll(String itemName, Integer page, Integer limit) {
        //设置pageHelper
        PageHelper.startPage(page,limit);

        //获得所有医疗设备信息
        List<Map> list = logisticsMaterialMapper.loadAll(itemName);

        //判断查询结果是否为空
        if (CollectionUtils.isEmpty(list)){
            throw new ShoppingException(ShoppingEnums.LMATERIAL_LIST_NOT_FOUND);
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
     * 删除
     * @param lmId
     */
    public void delLogisticsMaterial(String lmId) {
        int rows = logisticsMaterialMapper.deleteByPrimaryKey(lmId);
        if (rows==0){
            throw new ShoppingException(ShoppingEnums.LMATERIAL_DEL_NOT_FOUND);
        }
    }
}