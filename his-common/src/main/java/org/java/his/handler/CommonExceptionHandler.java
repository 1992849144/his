package org.java.his.handler;


import org.java.his.enums.ShoppingEnums;
import org.java.his.exception.ShoppingException;
import org.java.his.vo.ExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 公共异常处理类
 */
@ControllerAdvice
public class CommonExceptionHandler {

    /**
     * 出现异常以后，返回响应实体，返回的主体是ExceptionResult,里面包含了错误信息
     * @param ex
     * @return
     */
    @ExceptionHandler(ShoppingException.class)
    public ResponseEntity<ExceptionResult> handlerException(ShoppingException ex) {
        System.out.println("#################################@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        ShoppingEnums en = ex.getShoppingEnums();
        return ResponseEntity.status(en.getCode()).body(new ExceptionResult(en));
    }


//处理异常时，把枚举中错误信息显示出来，返回字符串
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<String> handlerException(ShoppingException ex){
//        return ResponseEntity.status(HttpStatus.CREATED).body(ex.getShoppingEnum().getMsg());
//    }
}
