package com.lc.realm;

import com.lc.pojo.User;
import com.lc.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行了授权 doGetAuthorizationInfo");
        SimpleAuthorizationInfo simpleInfo=new SimpleAuthorizationInfo();
        Subject subject= SecurityUtils.getSubject(); //获取当前用户的对象
        User user= (User) subject.getPrincipal();//获取用户先息
        simpleInfo.addStringPermission(user.getPerms());//获取权限
        return  simpleInfo;

    }
    //认证
    @Override
    protected SimpleAuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了认证 doGetAuthorizationInfo");
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken userToken= (UsernamePasswordToken) token;//获取登录信息
        System.out.println(userToken.getUsername());
        User query= userService.queryByName(userToken.getUsername());//获取用户姓名，密码，权限
        System.out.println(query);
        if (query==null){
            return null;//没有这个用户
        }
        Session session= subject.getSession();
        session.setAttribute("/userlogin ",query);

        if (!userToken.getUsername().equals(query.getUsername())){//判断登录的用户名密码 匹配数据库是否正确
            return null;//抛出异常
        }
        //密码认证
        return new SimpleAuthenticationInfo(query,query.getPassword(),"");
    }
}
