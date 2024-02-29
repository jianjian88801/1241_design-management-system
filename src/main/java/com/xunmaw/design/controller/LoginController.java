package com.xunmaw.design.controller;

import com.xunmaw.design.common.Result;
import com.xunmaw.design.common.StatusCode;
import com.xunmaw.design.domain.Administrator;
import com.xunmaw.design.domain.LoginDTO;
import com.xunmaw.design.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author chenchengjian
 * @date 2022/3/18 13:40
 * Description: 登录业务控制器
 */

@Slf4j
@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/login")
    public Result login(@RequestBody LoginDTO loginDTO, HttpServletRequest request){
        System.out.println(loginDTO);
        return loginService.login(loginDTO,request);
    }

    @RequestMapping(value = "/getUserInfo")
    public Result getUserInfo(HttpServletRequest request){
        HttpSession session = request.getSession();
        Administrator user = (Administrator) session.getAttribute("user");
        return  new Result(true, StatusCode.OK, "获取user成功",user);
    }

    //用户退出登录
    @RequestMapping("/user_logout")
    public String logout(HttpSession session){
        session.invalidate();      //清空session
        System.out.println("用户退出！");
        return "redirect:/login";  //重定向跳转到登录界面
    }


}
