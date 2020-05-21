package org.java.his.drugwarehouse.interceptor;


import org.java.auth.pojo.UserInfo;
import org.java.auth.utils.JwtUtils;
import org.java.his.drugwarehouse.conf.JwtProperties;
import org.java.his.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器类，解析token，获得Userinfo
 */
@Component
@EnableConfigurationProperties(JwtProperties.class)
public class DrugItercetpor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtProperties jwtProperties;

    //线程变量，用于存储用户信息
    private static final ThreadLocal<UserInfo> threadLocal = new ThreadLocal<>();

    /**
     * 该方法在会请求，进入控制器之前运行
     * 我们应该在该方法中解析token
     * 要解析token，我们需要使用JwtProperties属性类
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //解析token
        //从request中，获得cookie(通过CookieUtils工具类获取)中，存放的token
        String token  = CookieUtils.getCookieValue(request,jwtProperties.getCookieName());

        //通过JwtUtils类，从token解析，获得userinfo
        UserInfo userinfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());

        //将userinfo存储起来，在后面的controller,service中，都可以直接使用

        //将userinfo存储在线程变量中
        threadLocal.set(userinfo);
        //创建运行上下文，用于存储要渲染的数据
        return super.preHandle(request, response, handler);
    }


    /**
     * 本次请求即将结束时，执行，一般释放资源
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        threadLocal.remove();//释放线程变量
        super.afterCompletion(request, response, handler, ex);
    }

    /**
     * 调用该静态方法，即可从线程变量中，获得Userinfo
     * 其他位置，如果需要获得token中的 Userinfo，只需要执行  CartIntercetpor.getUserinfo()方法即可
     * @return
     */
    public static UserInfo getUserinfo(){
        return threadLocal.get();
    }
}
