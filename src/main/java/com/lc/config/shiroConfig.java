package com.lc.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.lc.realm.UserRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class shiroConfig {
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("getDefaultWebSecurityManager")DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);//设置安全管理器
        shiroFilterFactoryBean.setLoginUrl("/log");////没有认证后跳到的页面
        /**
         *          shiro的内置过滤器
         *          anon：无需认证就可以访问 默认
         *          authc：必须认证了才能访问
         *          user：必须拥有记住我功能才能访问
         *          perms：必须拥有对某个的权限才能访问
         *          role：拥有某个角色权限才能访问
         */
        //添加内置过滤器
        Map<String,String> filterChinaDefinitionMap=new LinkedHashMap<>();//拦截
        filterChinaDefinitionMap.put("/add","authc");//add请求必须认证才能访问
        filterChinaDefinitionMap.put("/del","authc");

        filterChinaDefinitionMap.put("/add","perms[user:id]");//授权，没有授权会被拦截
        filterChinaDefinitionMap.put("/del","perms[user:delete]");
        //未授权的跳转url
        shiroFilterFactoryBean.setUnauthorizedUrl("/Unauthorized");
        //把设置好的过滤设置到ShiroFilterFactoryBean
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChinaDefinitionMap);

        return shiroFilterFactoryBean;

    }


    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm")UserRealm userRealm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        securityManager.setRealm((Realm) userRealm);
        return securityManager;

    }

    //创建realm对象
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }
    //整合shiroDialect：用来整合thymeleaf
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

}
