package org.java.auth.service;

import org.hibernate.validator.constraints.Length;
import org.java.auth.config.JwtProperties;
import org.java.auth.feign.UserClient;
import org.java.auth.pojo.UserInfo;
import org.java.auth.utils.JwtUtils;
import org.java.his.managesuser.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private UserClient userClient;

    public String authentication(String username, String password) {

        //通过feign调用用户微服务的方法，进行认证操作
        User user = userClient.queryUser(username, password);
        if(user==null){
            return null;//用户信息错误，认证失败
        }

        try {
            //用户信息正确，准备把用户的id,name封装到JWT的userInfo对象中，然后把userInfo对象，放入载荷中
            UserInfo userInfo = new UserInfo();
            userInfo.setId(user.getId()); //用户id
            userInfo.setUsername(user.getUsername()); //用户名
            userInfo.setPicture(user.getPicture());  //用户图片
            String name = user.getRoles().get(0).getName(); //用户职位
            userInfo.setName(name);
            //把userinfo写入jwt
            //参数1:userinfo,参数2：私钥，在jwtProperties类可以动态读取，参数3：过期时间
            String token = JwtUtils.generateToken(userInfo, jwtProperties.getPrivateKey(), jwtProperties.getExpire());
            return token;//返回token
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; //失败返回null;
    }
}
