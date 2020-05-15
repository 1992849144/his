package org.java.his.user.web;


import org.java.his.user.pojo.User;
import org.java.his.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * data:要验证的数据
     * type:要验证的类型：   1：用户名，2：手机号
     * @return
     * 直接访问地址: http://localhost:15000/check/zhangsan/1
     * 网关访问地址: http://api.shopping.com/api/user/check/andy/1
     *
     * false:不可用
     * true:可用
     */
    @GetMapping("/check")
    public ResponseEntity<Boolean> check(@RequestParam("data") String data,@RequestParam("type") Integer type){

        Boolean flag = userService.check(data,type);
        if(flag==null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(flag);

    }


    /**
     * 用户注册
     * @param user
     * @return
     * 请求方式必须为:post
     *  直接访问地址: http://localhost:15000/register
     *  网关访问地址: http://api.shopping.com/api/user/register
     */
    @PostMapping("/register")
    public ResponseEntity<Void> userRegister( @Valid User user){

        //注册
        Boolean flag =  userService.register(user);

        //如果结果为null，或者为false，注册失败
        if(flag==null || !flag){
            return ResponseEntity.badRequest().build();
        }

        //HttpStatus.CREATED：201 Created 该请求已成功，并因此创建了一个新的资源。
        // 这通常是在POST请求，或是某些PUT请求之后返回的响应。
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    /**
     * 根据用户名，密码，查询数据
     * @return
     *  直接访问地址: http://localhost:15000/query?username=xx&password=xxx
     *  网关访问地址: http://api.shopping.com/api/user/query?username=xx&password=xxx
     */
    @GetMapping("query")
    public ResponseEntity<User>  queryUser(@RequestParam("username") String username,
                                           @RequestParam("password") String password){
        //根据用户名，密码查询
        User user= userService.queryUser(username,password);

        //判断查询结果
        if(user==null){
            //ResponseEntity.badRequest()：返回状态码400
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(user);
    }
}
