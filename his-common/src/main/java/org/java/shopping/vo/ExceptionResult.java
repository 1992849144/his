package org.java.shopping.vo;

import lombok.Data;
import org.java.shopping.enums.HisEnums;

/**
 * 用于在出错以后，封装要返回的数据
 */
@Data
public class ExceptionResult {

    private int code;//错误状态码
    private String msg;//异常消息内容
    private Long timestamp;//时间戳

    /**
     * 编写一个带参数的构造方法，参数是一个枚举
     * @param shoppingEnums
     */
    public ExceptionResult(HisEnums shoppingEnums){
        this.code = shoppingEnums.getCode();
        this.msg = shoppingEnums.getMsg();
        this.timestamp = System.currentTimeMillis();
    }

}
