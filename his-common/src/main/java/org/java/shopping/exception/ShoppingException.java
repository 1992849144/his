package org.java.shopping.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.java.shopping.enums.ShoppingEnums;

/**
 * 自定义异常类
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingException  extends RuntimeException{

    private ShoppingEnums shoppingEnums;

}
