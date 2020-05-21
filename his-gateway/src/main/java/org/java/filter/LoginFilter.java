package org.java.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.java.auth.pojo.UserInfo;
import org.java.auth.utils.JwtUtils;
import org.java.conf.FilterProperties;
import org.java.conf.JwtProperties;
import org.java.his.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component//标识组件，让容器扫描
@EnableConfigurationProperties({JwtProperties.class, FilterProperties.class})//加载属性类
public class LoginFilter extends ZuulFilter {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired //白名单属性类
    private FilterProperties filterProperties;

    /**
     * 标识这个过滤器的执行时机：
     *   1、pre_type:在路由之前执行(权限控制一般采用该类型)
     *   2、post_type:在路由之后执行
     *   3、error_type:在出错以后执行
     * @return
     */
    @Override
    public String filterType() {
        return PRE_TYPE; //当前是一个前置过滤器
    }

    /***
     * 标识，当前过滤器执行的顺序:  越小越优先
     * @return
     */
    @Override
    public int filterOrder() {
        return 5;
    }

    /**
     * 该方法，用于标识，当前请求是否进行过滤器的run方法，进行请求过滤
     * true:进入run方法执行过滤
     * false:不进行run方法，直接路由，找服务
     * @return
     */
    @Override
    public boolean shouldFilter() {

        //获得网关上下网
        RequestContext requestContext  = RequestContext.getCurrentContext();
        //获得请求
        HttpServletRequest request = requestContext.getRequest();
        //获得当前用户请求的路径
        String url = request.getRequestURL().toString();

        //System.out.println("请求的路径是:"+url);

        //获得白名单（要直接放行的路径）list--------------
        List<String>  allowPaths = filterProperties.getAllowPaths();

        //对集合中的路径循环，判断包含的单词是否出现在路径中，如果出现就放行
        for(String path :allowPaths){

            //判断路径中是否包含放行的路径
            //StringUtils.contains：底层是indexOf
            if(StringUtils.contains(url,path)){
                return false;//不进行run方法，直接路由
            }
        }

        return true;
    }

    /**
     * 该方法，用于具体过滤，处理请求
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {

        System.out.println("#################################进入网关过滤器的run方法，判断用户是否登录");

        //获得网关的上下文
        RequestContext requestContext  = RequestContext.getCurrentContext();

        //通过上下文得到请求
        HttpServletRequest request = requestContext.getRequest();

        //产生响应对象response
        HttpServletResponse response = requestContext.getResponse();

        //通过CookieUtils这个工具类，把request中携带的token取出来，进行验证是否正确
        //只要是经过网关代理的，如果不设置白名单，token是为空的
        String token = CookieUtils.getCookieValue(request, jwtProperties.getCookieName());

        //判断token是否为空，为空表示用户未登录
        if(StringUtils.isEmpty(token)){
            //未登录，程序不能向下执行，不能调用其他服务
            requestContext.setSendZuulResponse(false);//请求不再向下运行
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());//返回状态提示用户未登录
            //如果用户未登录，重定向到登录界面即可 //要重定向，需要有:
//            try {
//                response.sendRedirect("http://www.shopping.com/login");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }else{
            //token不为空，还需要判断，当前token是否是篡改过，或者是当前token已过期
            //只要能过该token可以获得用户信息即表示，token是有效的，如果无效，将会产生异常
            try {
                JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());//如果没有异常，信息正确
            } catch (Exception e) {
                e.printStackTrace();
                //有异常，表示token的信息不正确或者已经过期
                requestContext.setSendZuulResponse(false);//请求不再向下运行
                requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());//返回状态提示用户未登录
            }
        }

        //如果返回null,表示将进行路由，调用对应的服务---------请求继续向下运行
        return null;
    }
}
