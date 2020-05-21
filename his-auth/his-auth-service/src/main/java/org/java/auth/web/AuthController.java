package org.java.auth.web;

import org.apache.commons.lang3.StringUtils;
import org.java.auth.config.JwtProperties;
import org.java.auth.pojo.UserInfo;
import org.java.auth.service.AuthService;
import org.java.auth.utils.JwtUtils;
import org.java.his.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@EnableConfigurationProperties(JwtProperties.class)//加载属性配置类
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 通过网关访问地址: http://api.his.com/api/auth/accredit?username=jack&password=123
     * 直接访问地址: http://localhost:13000/accredit?username=jack&password=123
     */
    @PostMapping("/accredit")
    public ResponseEntity<Void> authentication(@RequestParam("username") String username,
                                               @RequestParam("password") String password,
                                               HttpServletRequest request,
                                               HttpServletResponse response){
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+username+","+password);
        //登录认证，调用service的方法，传递用户名，密码，通过jwt生成token
        String token = this.authService.authentication(username, password);
        System.out.println("token:"+token);
        //返回是否有token
        if(StringUtils.isBlank(token)){
            //登录失败
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        //有token,则应该通过shopping-common中的CookieUtil工具类，将token信息封装到cookie发送到客户端
        CookieUtils.setCookie(request,response,jwtProperties.getCookieName(),
                token,jwtProperties.getExpire()*60);
        return ResponseEntity.ok().build();
    }


    /***
     *
     * 验证用户是否登录,根据客户端请求携带的cookie,cookie中包含了shopping-token的信息
     * token通过jwt的公钥进行解密，如果可以得到userinfo信息，表示已登录。返回当前对象的用户信息
     * 如果未登录，则会返回错误状态码
     * 并且，如果用户验证一次并且通过，表示用户在使用页面，jwt中的设置的有效时间，以及cookie中的存活时间，都应该延长
     *通过网关访问地址: http://api.his.com/api/auth/verify
     *直接访问地址: http://localhost:13000/verify
     * @param token
     * @return
     */
    @GetMapping("/verify")
    public ResponseEntity<UserInfo> verify(@CookieValue("his-token") String token,HttpServletRequest request,HttpServletResponse response){
        System.out.println("===========token======"+token);

        try {
            UserInfo userInfo  = JwtUtils.getInfoFromToken(token,jwtProperties.getPublicKey());
            if(userInfo==null){
                //返回状态码401
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            //System.out.println(userInfo.getId()+","+userInfo.getUsername());

            //用户已登录，将jwt中的有效时间及cookie存活时间延迟长
            //产生一个新的token,覆盖之前的token，时间就会重新为30分钟，载荷还是之前的载荷
            token=JwtUtils.generateToken(userInfo,jwtProperties.getPrivateKey(),jwtProperties.getExpire());

            //重新设置cookie的有效时间
            CookieUtils.setCookie(request,response,jwtProperties.getCookieName(),token,jwtProperties.getExpire()*60);

            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * 退出登录
     * 修改cookie的过期时间
     * @param token
     * @return
     * http://api.his.com/api/auth/logout
     */
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@CookieValue("his-token") String token,
                                       HttpServletRequest request,
                                       HttpServletResponse response){
        System.out.println("要过期的cookie:"+token);

        //设置当前cookie在一秒以后，过期
        CookieUtils.setCookie(request,response,jwtProperties.getCookieName(),token,1);

        return ResponseEntity.ok().build();
    }
}
