package org.java.his.managesuser.service;

import org.apache.commons.lang.StringUtils;
import org.java.his.enums.ShoppingEnums;
import org.java.his.exception.ShoppingException;
import org.java.his.managesuser.dao.RoleMapper;
import org.java.his.managesuser.dao.UserMapper;
import org.java.his.managesuser.pojo.Role;
import org.java.his.managesuser.pojo.User;
import org.java.his.managesuser.utils.CodecUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 根据用户名，密码，查询数据
     * @return
     * 	1、判断用户名是否存在，如果存在，返回正确的用户信息
     * 	2、然后将用户输入的密码通过md5加密，与查询到的正确密码进行比较
     */
    public User queryUser(String username,String password){

        //根据用户名执行查询
        User user = userMapper.queryUser(username);

        //判断是否有该用户名称
        if (user== null){
            return null; //判断是否有该用户名称
        }

        //信息不为空，得到从数据库中，查询的salt
        String salt = user.getSalt();

        //对用户输入的pwd采用md5,加盐，加密以后，与查询的正确密码，进行比较
        String pwd  = CodecUtils.md5Hex(password, salt);

        if (StringUtils.equals(pwd,user.getPassword())){
            //密码一致，信息正确
            return user;
        }
        return null;
    }

    /**
     * 修改用户信息
     * @param map
     */
    public void putUser(Map map){
        //从map中获得用户信息
        User user=new User();
        //获得id
        user.setId(map.get("id").toString());  //赋值id
        user.setUsername(map.get("username").toString()); //赋值用户名
        user.setPicture(map.get("image").toString());   //赋值图片

        //密码加密
        //导入工具类，生成盐(随机生成)
        String salt = CodecUtils.generateSalt();

        //将盐设置user中，存储到数据库
        user.setSalt(salt);

        //用户输入的密码，并没有加密，不安全，所以，我们要采用md5，加盐加密
        String pwd = CodecUtils.md5Hex(map.get("password").toString(),salt);
        user.setPassword(pwd);

        int rows = userMapper.updateByPrimaryKey(user);
        if (rows==0){
            throw new ShoppingException(ShoppingEnums.MANAGESUSER_PUT_FALLURE);
        }
    }
}
