package com.xunmaw.design.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author chenchengjian
 * @date 2022/3/14 22:45
 * Description:管理员类
 */

@Table(name = "tb_administrator")
public class Administrator implements Serializable {

    @Id
    private Integer Id ;
    private String uname;
    private String password;
    private Integer type;
    private String name;
    private Integer depart_id;
    private Integer major_id;
    private Integer status;

    private Depart depart;

    public Depart getDepart() {
        return depart;
    }

    public void setDepart(Depart depart) {
        this.depart = depart;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDepart_id() {
        return depart_id;
    }

    public void setDepart_id(Integer depart_id) {
        this.depart_id = depart_id;
    }

    public Integer getMajor_id() {
        return major_id;
    }

    public void setMajor_id(Integer major_id) {
        this.major_id = major_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "Id=" + Id +
                ", uname='" + uname + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", depart_id=" + depart_id +
                ", major_id=" + major_id +
                ", status=" + status +
                ", \n   depart=" + depart +
                '}';
    }
}
