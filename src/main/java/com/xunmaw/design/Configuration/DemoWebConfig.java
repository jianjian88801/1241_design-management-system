package com.xunmaw.design.Configuration;

import com.xunmaw.design.interceptor.Dept_Manager_Interceptor;
import com.xunmaw.design.interceptor.LoginInterceptor;
import com.xunmaw.design.interceptor.Manager_Interceptor;
import com.xunmaw.design.interceptor.Student_Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @Author: 三分恶
 * @Date: 2021/1/18
 * @Description: web配置
 **/
@Configuration
public class DemoWebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;
    @Autowired
    private Manager_Interceptor managerInterceptor;
    @Autowired
    private Dept_Manager_Interceptor deptManagerInterceptor;
    @Autowired
    private Student_Interceptor studentInterceptor;

    // 这个方法是用来配置静态资源的，比如html，js，css，等等
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    // 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns("/**") 表示拦截所有的请求，
        // excludePathPatterns("/login", "/register") 表示除了登陆与注册之外，因为登陆注册不需要登陆也可以访问
        //登录拦截器
        registry.addInterceptor(loginInterceptor).addPathPatterns(
                "/Manager/**",
                "/Dept_Manager/**",
                "/Manager.html",
                "/Dept_Manager.html",
                "/Student.html"
        ).excludePathPatterns(
                "/api/**",
                "/login.html",
                "/css/**",
                "/js/**",
                "/images/**",
                "/activity/**",
                "/admin/**",
                "/lib/**",
                "/ueditor/**"
        );
        //教务管理员权限拦截器
        registry.addInterceptor(managerInterceptor).addPathPatterns(
                "/DeptManager/**",
                "/Dept_Manager.html",
                "/Student.html"
        ).excludePathPatterns(
                "/api/**",
                "/login.html",
                "/css/**",
                "/js/**",
                "/images/**",
                "/activity/**",
                "/admin/**",
                "/lib/**",
                "/ueditor/**"
        );
        //系部管理员权限拦截器
        registry.addInterceptor(deptManagerInterceptor).addPathPatterns(
                "/Manager/**",
                "/Manager.html",
                "/Student.html"
        ).excludePathPatterns(
                "/api/**",
                "/login.html",
                "/css/**",
                "/js/**",
                "/images/**",
                "/activity/**",
                "/admin/**",
                "/lib/**",
                "/ueditor/**"
        );
        //学生拦截器
        registry.addInterceptor(studentInterceptor).addPathPatterns(
                "/Manager/**",
                "/Manager.html",
                "/DeptManager/**",
                "/Dept_Manager.html"
        ).excludePathPatterns(
                "/api/**",
                "/login.html",
                "/css/**",
                "/js/**",
                "/images/**",
                "/activity/**",
                "/admin/**",
                "/lib/**",
                "/ueditor/**"
        );
    }

    //设置默认页面
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/login.html");
        //设置优先级  当请求地址有重复的时候  执行优先级最高的
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        WebMvcConfigurer.super.addViewControllers(registry);
    }
}



