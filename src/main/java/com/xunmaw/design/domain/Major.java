package com.xunmaw.design.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author chenchengjian
 * @date 2022/2/19 23:35
 * Description:专业类
 */

@Table(name = "tb_major")
public class Major implements Serializable {

    @Id
    private Integer majorId;
    private String ProNumber;
    private String majorName;
    private Integer majorDepartId;

    private Depart depart;

    public Depart getDepart() {
        return depart;
    }

    public void setDepart(Depart depart) {
        this.depart = depart;
    }

    public Major(String proNumber) {
        ProNumber = proNumber;
    }

    public Major() {

    }


    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    public String getProNumber() {
        return ProNumber;
    }

    public void setProNumber(String proNumber) {
        ProNumber = proNumber;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public Integer getMajorDepartId() {
        return majorDepartId;
    }

    public void setMajorDepartId(Integer majorDepartId) {
        this.majorDepartId = majorDepartId;
    }

    @Override
    public String toString() {
        return "Major{" +
                "majorId=" + majorId +
                ", ProNumber='" + ProNumber + '\'' +
                ", majorName='" + majorName + '\'' +
                ", majorDepartId=" + majorDepartId +
                ", \n   depart=" + depart +
                '}';
    }
}
