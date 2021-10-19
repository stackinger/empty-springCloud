package com.example.filter;

import com.example.model.User;
import com.example.properties.FilterProperties;
import com.example.properties.JwtProperties;
import com.example.utils.JwtUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Component
//2.1 加载JWT配置类
@EnableConfigurationProperties({JwtProperties.class , FilterProperties.class} )
public class TokenFilter  extends ZuulFilter {

    //2.2 注入jwt配置类实例
    @Resource
    private JwtProperties jwtProperties;

    @Resource
    private FilterProperties filterProperties;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 5;
    }

    @Override
    public boolean shouldFilter() {
        //03.当前过滤器是否执行，true执行（进run方法），false不执行（根据请求路径进对应的服务）
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String requestURI = request.getRequestURI();
        // 从FilterProperties类中 获取 application.properties 文件中的白名单（就是不过网关的路径）
        for (String allowPath : filterProperties.getAllowPaths()) {
            // 请求路径包含集合中的就是白名单路径，不过zuul过滤器
            if (requestURI.contains(allowPath)){
                return false;
            }
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //1 获得token
        //1.1 获得上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        //1.2 获得request对象
        HttpServletRequest request = currentContext.getRequest();
        //1.3 获得指定请求头的值
        String token = request.getHeader("Authorization");


        //2 校验token -- 使用JWT工具类进行解析
        // 2.3 使用工具类，通过公钥获得对应信息
        try {
            //用户名密码
            User fromToken = JwtUtils.getObjectFromToken(token, jwtProperties.getPublicKey(), User.class);
            System.out.println("user");

        } catch (Exception e) {
            // 2.4 如果有异常--没有登录（没有权限）
            currentContext.addOriginResponseHeader("content-type","text/html;charset=UTF-8");
            currentContext.addZuulResponseHeader("content-type","text/html;charset=UTF-8");
            currentContext.setResponseStatusCode( 403 );        //响应的状态码：403
            currentContext.setResponseBody("token失效或无效");
            currentContext.setSendZuulResponse( false );        //没有响应内容
        }

        // 2.5 如果没有异常，登录了--放行
        return null;
    }
}


