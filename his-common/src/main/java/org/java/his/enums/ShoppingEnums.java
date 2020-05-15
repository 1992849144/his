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
    DRUGWAREHOUSE_PUUPRICEADJUSTMENT_FAILURE(400,"药品修改价格失败"),
    DRUGWAREHOUSE_PUTRECEIPT_FAILURE(400,"药品添加库存失败"),
    DRUGWAREHOUSE_UPDATE_FAILURE(400,"药品修改失败"),
    DRUGWAREHOUSE_DELETE_FAILURE(400,"药品删除失败"),
    DRUGWAREHOUSE_LIST_NOT_FOUND(404,"药品列表不存在"),
    DRUGWAREHOUSE_ADD_NOT_FOUND(400,"药品新增入库房失败");
    //枚举中的字段
    private int code;//错误状态码
    private String msg;//错误消息
}
