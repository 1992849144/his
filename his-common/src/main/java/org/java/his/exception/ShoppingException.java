package org.java.his.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.java.his.enums.ShoppingEnums;

/**
 * 自定义异常类
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingException  extends RuntimeException{

    private ShoppingEnums shoppingEnums;
}
