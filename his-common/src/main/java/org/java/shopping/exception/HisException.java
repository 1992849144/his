package org.java.shopping.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.java.shopping.enums.HisEnums;

/**
 * 自定义异常类
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HisException extends RuntimeException{

    private HisEnums hisEnums;

}
