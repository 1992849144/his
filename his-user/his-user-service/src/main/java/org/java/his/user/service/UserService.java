package org.java.his.user.service;

import org.apache.commons.lang.StringUtils;
import org.java.his.user.dao.UserMapper;
import org.java.his.user.pojo.User;
import org.java.his.user.utils.CodecUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 检测，用户信息是否可用
     * data:要验证的数据
     * type:要验证的类型：   1：用户名，2：手机号
     * @return
     */
    public  Boolean check(String data, Integer type) {

        //创建User对象，封装查询条件
        User recode = new User();

        //判断查询条件
        if(type==1){
            recode.setUsername(data);
        }else if(type==2){
            recode.setPhone(data);
        }else{
            return null;//数据有误
        }

        //根据User对象的条件，执行查询
        //selectCount:实体类有没有几个条件就按几个条件查询 ，返回的类型是受影响的行数为0表示数据库不存在，返回true
        //==:表示判断
        //userMapper.selectCount(recode)==0：表示true,反正返回false
        Boolean flag = userMapper.selectCount(recode)==0;  //如果根据用户名，或电话，查询不到数据，该信息可用

        return flag;
    }

    /**
     * 用户注册的方法
     * @param user
     * @return
     */
    public Boolean register(User user) {

        //设置用户的注册时间
        user.setCreated(new Date());

        //导入工具类，生成盐(随机生成)
        String salt = CodecUtils.generateSalt();

        //将盐设置user中，存储到数据库
        user.setSalt(salt);

        //用户输入的密码，并没有加密，不安全，所以，我们要采用md5，加盐加密
        String pwd = CodecUtils.md5Hex(user.getPassword(),salt);
        user.setPassword(pwd);

        //注册对象
        //insertSelective：选择性(不为null)的添加，返回的类型是受影响的行数为1表示成功
        Boolean flag  = userMapper.insertSelective(user)==1;

        return flag;
    }

    /**
     * 根据用户名，密码，查询数据
     * @return
     * 	1、判断用户名是否存在，如果存在，返回正确的用户信息
     * 	2、然后将用户输入的密码通过md5加密，与查询到的正确密码进行比较
     */
    public User queryUser(String username, String password) {
        //创建一个User对象，用于封装查询条件
        User recode=new User();
        recode.setUsername(username);

        //根据用户名执行查询
        User user = userMapper.selectOne(recode);

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
}
