package com.lc.controller;

import com.lc.pojo.User;
import com.lc.service.UserService;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.omg.CORBA.portable.UnknownException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class myController {

    @Autowired
    UserService userService;

    @RequestMapping("/log")
    public String loginIn(){
        return "login";
    }

    @RequestMapping("/query")
    @ResponseBody
    public void query(){
        User jack = userService.queryByName("jack");
        System.out.println(jack);
    }

    @RequestMapping("/pwd")
    @ResponseBody
    public void queryPwd(){
        User user= userService.queryByPassword("12");

        System.out.println(user);

    }

    @RequestMapping("/login")
    public String login(String username,String password){
        try{
            Subject subject= SecurityUtils.getSubject();
            UsernamePasswordToken token=new UsernamePasswordToken(username,password);
            subject.login(token);
            return "index";
        }catch (UnknownAccountException e){
            e.printStackTrace();
            System.out.println("用户不存在！");
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            System.out.println("密码错误");
        }
        return "login";
    }
    @RequestMapping("/add")
    public String adduser(){
        return "add";
    }

    @RequestMapping("/delete")
    public String deleteuser(){
        return "delete";
    }
}
