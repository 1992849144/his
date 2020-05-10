package org.java.shopping.handler;

import org.java.shopping.enums.ShoppingEnums;
import org.java.shopping.exception.ShoppingException;
import org.java.shopping.vo.ExceptionResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 公共的，异常处理类
 */
@ControllerAdvice
public class CommonExceptionHandler {

    /**
     * 如果有运行时异常，该方法会自动进行处理
     * @param ex
     * @return
     */
    @ExceptionHandler(ShoppingException.class)
    public ResponseEntity<ExceptionResult> handleException(ShoppingException ex){

        //获得自定义异常的中枚举ShoppingEnums,枚举中，包含两个值: 状态码与错误消息
        ShoppingEnums en = ex.getShoppingEnums();


        //创建对象，封装返回结果
        ExceptionResult result = new ExceptionResult(en);
        return ResponseEntity.status(en.getCode()).body(result);
    }



    /**
     * 如果有运行时异常，该方法会自动进行处理
     * @param ex
     * @return
     */
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<String> handleException(RuntimeException ex){
//        System.out.println("##########################");
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
//    }
}
