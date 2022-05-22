package com.cy.store.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//定义一个拦截器
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 检测全局session对象中是否有uid数据，如果有则方向，如果没有则重定向到登录页面
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器（url+Controller的映射）
     * @return 如果返回值为true表示为放行当前的请求，如果返回值为false表示为拦截当前的请求
     * @throws Exception
     * 根据session的返回值来动态的设置返回值为true/false
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //可以通过HttpServletRequest对象来获取session
        Object obj = request.getSession().getAttribute("uid");
        if(obj==null){
            //说明用户没有登录过系统，则重定向到登录页面
            //重新告诉前端，新的页面在哪个目录的新的页面
            response.sendRedirect("/web/login.html");
            //结束后续的调用
            return false;
        }
        //请求放行
        return true;
    }
}
