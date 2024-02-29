package com.xunmaw.design.service;

import com.xunmaw.design.common.Result;
import com.xunmaw.design.domain.LoginDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author chenchengjian
 * @date 2022/3/18 13:44
 * Description:
 */
public interface LoginService {
    Result login(LoginDTO loginDTO, HttpServletRequest request);
}
