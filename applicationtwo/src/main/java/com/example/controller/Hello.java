package com.example.controller;

import com.example.model.User;
import com.example.properties.JwtProperties;
import com.example.utils.JwtUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class Hello {

    @Resource
    private JwtProperties jwtProperties;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String helloWorld(){
        System.out.println("two");
        return "two-hello word";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() throws Exception{

        System.out.println("用户登录");
        //校验验证码--使用后删除
        /*String redisCode = stringRedisTemplate.opsForValue().get( "login" + user.getUsername() );
        stringRedisTemplate.delete( "login" + user.getUsername() );
        if(redisCode == null) {
            return BaseResult.error("验证码无效");
        }
        if(! redisCode.equalsIgnoreCase(user.getCode())) {
            return BaseResult.error("验证码错误");
        }*/
        //登录
        // User loginUser = authService.login(user);
        User loginUser = new User();
        loginUser.setUserName("admin");
        loginUser.setPassword("123456");
        if(loginUser != null ) {
            System.out.println("登录成功");
            //生成Token
            String token = JwtUtils.generateToken(loginUser, jwtProperties.getExpire(), jwtProperties.getPrivateKey());
            // return BaseResult.ok("登录成功").append("loginUser",loginUser).append("token",token);
            return token;
        } else {
//            return BaseResult.error("用户名或密码不匹配");
            return "用户名或密码不匹配";
        }
    }
}
