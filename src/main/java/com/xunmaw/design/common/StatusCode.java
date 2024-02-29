package com.xunmaw.design.common;

/**
 * @date 2022/1/22 15:06
 * Description:标准返回码code
 */
public class StatusCode {
    public static final int OK=2000;//成功
    public static final int ERROR =2001;//失败
    public static final int LOGINERROR =2002;//用户名或密码错误
    public static final int ACCESSERROR =2003;//权限不足
    public static final int REMOTEERROR =2004;//远程调用失败
    public static final int REPERROR =2005;//重复操作
}
