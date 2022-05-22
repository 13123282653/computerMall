package com.cy.store.config;

import com.cy.store.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

//登录过滤的配置类——处理器拦截器的注册
@Configuration//加载当前的拦截器配置类并进行调用
public class LoginInterceptorConfigurer implements WebMvcConfigurer {
    //配置拦截器
    @Override
    //这个方法是将自定义的拦截器进行注册
    public void addInterceptors(InterceptorRegistry registry) {
        //创建自定义的拦截器对象
        HandlerInterceptor interceptor=new LoginInterceptor();

        //配置拦截器的白名单：因为源代码要求返回一个List集合
        List<String> patterns=new ArrayList<>();
        /**静态资源**/
        patterns.add("/bootstrap3/**");
        patterns.add("/css/**");
        patterns.add("/images/**");
        patterns.add("/js/**");
        /**web下的某些页面**/
        patterns.add("/web/login.html");
        patterns.add("/web/register.html");
        patterns.add("/web/product.html");
        patterns.add("/web/index.html");//是resources/web/index.html，而不是resources/index.html
        patterns.add("/products/**");


        /**用户的一些数据？**/
        patterns.add("/users/reg");
        patterns.add("/users/login");
        patterns.add("/districts/**");

        //registry.addInterceptor()表示：向这个注册器对象把拦截器添加，就相当于把拦截器注册了
        //完成拦截器的注册
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")//addPathPatterns:表示拦截的url是什么,/**表示拦截所有的路径请求
                .excludePathPatterns(patterns);//excludePathPatterns:表示不拦截哪些url，返回的是一个集合
    }
}
