package com.xunmaw.design.Util;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @Author it08
 * @Date 2023/3/1 14:25
 * @Version 1.0
 */
public class SessionListener implements HttpSessionListener {
    private int onlineCount = 0;//记录session的数量

    /**
     * session创建后执行
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        onlineCount++;
        System.out.println("【HttpSessionListener监听器】 sessionCreated, onlineCount:" + onlineCount);
        //将最新的onlineCount值存起来
        se.getSession().getServletContext().setAttribute("onlineCount", onlineCount);

    }

    /**
     * session失效后执行
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        if (onlineCount > 0) {
            onlineCount--;
        }
        System.out.println("【HttpSessionListener监听器】 sessionDestroyed, onlineCount:" + onlineCount);
        //将最新的onlineCount值存起来
        se.getSession().getServletContext().setAttribute("onlineCount", onlineCount);
    }
}
