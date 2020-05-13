package org.java.shopping.enums;

import lombok.*;

/**
 * 创建枚举，包含一些常见的错误的消息
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum HisEnums {
    //枚举值，相当于构造函数
    DRUGWAREHOUSE_ADD_FAILURE(400,"药品修改失败"),
    DRUGWAREHOUSE_DELETE_FAILURE(400,"药品删除失败"),
    DRUGWAREHOUSE_LIST_NOT_FOUND(404,"药品列表不存在"),
    DRUGWAREHOUSE_ADD_NOT_FOUND(400,"药品新增入库房失败");
    //枚举中的字段
    private int code;//错误状态码
    private String msg;//错误消息
}
