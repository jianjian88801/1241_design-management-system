package com.xunmaw.design.domain;

/**
 * @author chenchengjian
 * @date 2022/3/17 14:24
 * Description:学生模板类
 */
public class StudentTemplate {

    private Integer id;
    private String StuNumber;//学生学号
    private String StuName;
    private String ProNumber;
    private String StuClass;
    private String phoneNumber;
    private String campus;
    private Integer session;//届数

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStuNumber() {
        return StuNumber;
    }

    public void setStuNumber(String stuNumber) {
        StuNumber = stuNumber;
    }

    public String getStuName() {
        return StuName;
    }

    public void setStuName(String stuName) {
        StuName = stuName;
    }

    public String getProNumber() {
        return ProNumber;
    }

    public void setProNumber(String proNumber) {
        ProNumber = proNumber;
    }

    public String getStuClass() {
        return StuClass;
    }

    public void setStuClass(String stuClass) {
        StuClass = stuClass;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public Integer getSession() {
        return session;
    }

    public void setSession(Integer session) {
        this.session = session;
    }

    @Override
    public String toString() {
        return "StudentTemplate{" +
                "id=" + id +
                ", StuNumber='" + StuNumber + '\'' +
                ", StuName='" + StuName + '\'' +
                ", ProNumber='" + ProNumber + '\'' +
                ", StuClass='" + StuClass + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", campus='" + campus + '\'' +
                ", session=" + session +
                '}';
    }
}
