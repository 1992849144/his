package org.java.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 创建枚举，包含一些常见的错误的消息
 */
@Getter
@AllArgsConstructor
//@NoArgsConstructor
public enum ShoppingEnums {

    //枚举值，相当于构造函数
    DRUGAPPLICATION_DEL_NOT_FOUND(400,"药品申请删除失败"),
    LMATERIAL_DEL_NOT_FOUND(400,"领料单删除失败"),
    LMATERIAL_LIST_NOT_FOUND(404,"领料单列表不存在"),
    LMATERIAL_ADD_NOT_FOUND(400,"新增后勤库房领料单失败"),
    LOGISTICS_DEL_NOT_FOUND(404,"后勤库房删除失败"),
    LOGISTICS_LIST_NOT_FOUND(404,"后勤库房列表不存在"),
    LOGISTICS_ADD_NOT_FOUND(400,"后勤库房入库新增失败"),
    MATERIAL_DEL_NOT_FOUND(400,"医疗设备领料单删除失败"),
    MATERIAL_ADD_NOT_FOUND(400,"医疗设备新增领料单失败"),
    MEDICAL_PUT_NOT_FOUND(400,"医疗设备退款失败"),
    MEDICAL_UPDATE_NOT_FOUND(400,"医疗设备付款失败"),
    MEDICAL_DEL_NOT_FOUND(400,"医疗设备删除失败"),
    MEDICAL_LIST_NOT_FOUND(404,"医疗设备列表不存在"),
    MEDICAL_ADD_NOT_FOUND(400,"医疗设备新增入库失败"),
    MEDICINE_DEL_NOT_FOUND(404,"药品出库删除失败"),
    MEDICINE_LIST_NOT_FOUND(404,"药品出库列表不存在"),
    MEDICINE_ADD_NOT_FOUND(400,"药品添加出库失败"),
    DRUGAPPLICATION_ADD_NOT_FOUND(400,"药品申请失败"),
    BREAKAGE_DEL_FALLURE(400,"药品报损删除失败"),
    BREAKAGE_UPDATE_FALLURE(400,"药品报损修改失败"),
    SYSDRUGPRICING_DEL_FALLURE(400,"药品调价删除失败"),
    SYSDRUGPRICING_LISTALL_FALLURE(404,"药品调价历史订单列表不存在"),
    SYSDRUGPRICING_UPDATE_FALLURE(400,"药品调价修改失败"),
    SYSBREAKAGE_ADD_FALLURE(400,"药品报损新增失败"),
    SYSDRUGPRICING_LIST_FALLURE(404,"药品调价列表不存在"),
    SYSDRUGPRICING_ADD_FALLURE(400,"药品调价新增失败"),
    MANAGESUSER_PUT_FALLURE(400,"用户修改信息失败"),
    DRUGWAREHOUSE_PUUPRICEADJUSTMENT_FAILURE(400,"药品修改价格失败"),
    DRUGWAREHOUSE_PUTRECEIPT_FAILURE(400,"药品添加库存失败"),
    DRUGWAREHOUSE_UPDATE_FAILURE(400,"药品修改失败"),
    DRUGWAREHOUSE_DELETE_FAILURE(400,"药品删除失败"),
    DRUGWAREHOUSE_LIST_NOT_FOUND(404,"药品列表不存在"),
    DRUGWAREHOUSE_ADD_NOT_FOUND(400,"药品新增入库失败");
    //枚举中的字段
    private int code;//错误状态码
    private String msg;//错误消息
}
