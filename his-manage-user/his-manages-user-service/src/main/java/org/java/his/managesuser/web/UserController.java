package org.java.his.managesuser.web;

import org.java.his.managesuser.pojo.User;
import org.java.his.managesuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据用户名，密码，查询数据
     * @return
     *  直接访问地址: http://localhost:12000/query?username=admin&password=123
     *  网关访问地址: http://api.his.com/api/managesuser/query?username=admin&password=123
     */
    @GetMapping("/query")
    public ResponseEntity<User> queryUser(@RequestParam("username")String username,
                                           @RequestParam("password") String password){
        //根据用户名，密码查询
        User user = userService.queryUser(username,password);

        //判断查询结果

        if(user==null){
            //ResponseEntity.badRequest()：返回状态码400
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(user);
    }

    /**
     * 修改用户信息
     *  直接访问地址: http://localhost:12000/putUser
     *  网关访问地址: http://api.his.com/api/managesuser/putUser
     * @param map
     * @return
     */
    @PutMapping("putUser")
    public ResponseEntity<Void> putUser(@RequestParam Map map){
        userService.putUser(map);
        return ResponseEntity.ok().build();
    }
}
