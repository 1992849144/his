package org.java.shopping.enums;

import lombok.*;

/**
 * 创建枚举，包含一些常见的错误的消息
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ShoppingEnums {

    //枚举值，相当于构造函数
    SKU_UPDATE_FAILURE(400,"修改商品失败"),
    SKU_ADD_FAILURE(400,"商品新增失败"),
    SKU_SELECT_FAILURE(400,"查询商品失败"),
    SKU_DELETE_FAILURE(400,"商品删除失败"),
    SKU_LIST_NOT_FOUND(404,"商品列表不存在"),
    BRAND_UPDATE_FAILURE(400,"修改商品品牌失败"),
    BRAND_SELETE_FAILURE(400,"查询商品品牌失败"),
    BRAND_ADD_FAILURE(400,"商品品牌新增失败"),
    BRAND_DELETE_FAILURE(400,"商品品牌删除失败"),
    BRAND_LIST_NOT_FOUND(404,"商品品牌列表不存在"),
    CATEGORY_LIST_NOT_FOUND(404,"商品类别列表不存在"),
    PRICE_CANNOT_BE_NOT(400,"价格不允许为空"),
    ITEM_NOT_FOUND(404,"商品不存在");
    //枚举中的字段
    private int code;//错误状态码
    private String msg;//错误消息
}
