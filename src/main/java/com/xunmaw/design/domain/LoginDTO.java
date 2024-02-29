package com.xunmaw.design.domain;

/**
 * @author chenchengjian
 * @date 2022/3/18 13:39
 * Description: 登录业务实体类
 */
public class LoginDTO {

    private String loginName;
    private String password;
    private Integer type;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                '}';
    }
}
