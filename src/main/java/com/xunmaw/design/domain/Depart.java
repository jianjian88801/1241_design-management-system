package com.xunmaw.design.domain;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author chenchengjian
 * @date 2022/2/17 16:38
 * Description:系部/学院类
 */
@Table(name = "tb_depart")
public class Depart {

    @Id
    private Integer depId;
    private String depName;
    private Integer majorNum;
    private Major major;

    public Depart() {
    }

    public Depart(String departName) {
    }


    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public Depart(Integer depId) {
        this.depId = depId;
    }

    public Integer getDepId() {
        return depId;
    }

    public void setDepId(Integer depId) {
        this.depId = depId;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public Integer getMajorNum() {
        return majorNum;
    }

    public void setMajorNum(Integer majorNum) {
        this.majorNum = majorNum;
    }

    @Override
    public String toString() {
        return "Depart{" +
                "depId=" + depId +
                ", depName='" + depName + '\'' +
                ", majorNum=" + majorNum +
                '}';
    }
}
