package com.xunmaw.design.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @date 2022/2/15 21:51
 * Description:教师类
 */

@Table(name = "tb_teacher")
public class Teacher implements Serializable {

    @Id
    private Integer TeaId;
    private String TeaNo;//教师工号
    private String TeaName;
    private String Password;
    private Integer DepartId;
    private String phoneNumber;
    private String JobTitle;//职称
    private Integer gender;
    private String XueWei;//学位
    private Integer status;
    @Transient
    private Integer studentnumbers; // 分配学生人数
    private TeaTarget tea_target;   //教师指标

    public TeaTarget getTea_target() {
        return tea_target;
    }

    public void setTea_target(TeaTarget tea_target) {
        this.tea_target = tea_target;
    }

    public Teacher(Integer teacher_id) {
    }

    public Teacher() {

    }

    public Teacher(Integer teaId, String teaNo, String teaName, String password, Integer departId, String phoneNumber, String jobTitle, Integer gender, String xueWei, Integer status, Integer studentNumbers, String departName, ArrayList<Integer> majors, Depart depart, List<Major> majorList) {
        TeaId = teaId;
        TeaNo = teaNo;
        TeaName = teaName;
        Password = password;
        DepartId = departId;
        this.phoneNumber = phoneNumber;
        JobTitle = jobTitle;
        this.gender = gender;
        XueWei = xueWei;
        this.status = status;
        studentnumbers = studentnumbers;
        DepartName = departName;
        this.majors = majors;
        this.depart = depart;
        this.majorList = majorList;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "TeaId=" + TeaId +
                ", TeaNo='" + TeaNo + '\'' +
                ", TeaName='" + TeaName + '\'' +
                ", Password='" + Password + '\'' +
                ", DepartId=" + DepartId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", JobTitle='" + JobTitle + '\'' +
                ", gender=" + gender +
                ", XueWei='" + XueWei + '\'' +
                ", status=" + status +
                ", studentnumbers=" + studentnumbers +
                ", DepartName='" + DepartName + '\'' +
                ", majors=" + majors +
                ", depart=" + depart +
                ", majorList=" + majorList +
                ",\n     TeaTarget=" + tea_target +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(TeaId, teacher.TeaId) && Objects.equals(TeaNo, teacher.TeaNo) && Objects.equals(TeaName, teacher.TeaName) && Objects.equals(Password, teacher.Password) && Objects.equals(DepartId, teacher.DepartId) && Objects.equals(phoneNumber, teacher.phoneNumber) && Objects.equals(JobTitle, teacher.JobTitle) && Objects.equals(gender, teacher.gender) && Objects.equals(XueWei, teacher.XueWei) && Objects.equals(status, teacher.status) && Objects.equals(studentnumbers, teacher.studentnumbers) && Objects.equals(tea_target, teacher.tea_target) && Objects.equals(DepartName, teacher.DepartName) && Objects.equals(majors, teacher.majors) && Objects.equals(depart, teacher.depart) && Objects.equals(majorList, teacher.majorList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(TeaId, TeaNo, TeaName, Password, DepartId, phoneNumber, JobTitle, gender, XueWei, status, studentnumbers, tea_target, DepartName, majors, depart, majorList);
    }

    public Integer getStudentNumbers() {
        return studentnumbers;
    }

    public void setStudentNumbers(Integer studentnumbers) {
        studentnumbers = studentnumbers;
    }

    public String getDepartName() {
        return DepartName;
    }

    public void setDepartName(String departName) {
        DepartName = departName;
    }

    private String DepartName;

    private ArrayList<Integer> majors;//指导专业id数组
    private Depart depart;
    private List<Major> majorList;//指导专业


    public ArrayList<Integer> getMajors() {
        return majors;
    }

    public void setMajors(ArrayList<Integer> majors) {
        this.majors = majors;
    }

    public List<Major> getMajorList() {
        return majorList;
    }

    public void setMajorList(List<Major> majorList) {
        this.majorList = majorList;
    }

    public Depart getDepart() {
        return depart;
    }

    public void setDepart(Depart depart) {
        this.depart = depart;
    }

    public Integer getTeaId() {
        return TeaId;
    }

    public void setTeaId(Integer teaId) {
        TeaId = teaId;
    }

    public String getTeaNo() {
        return TeaNo;
    }

    public void setTeaNo(String teaNo) {
        TeaNo = teaNo;
    }

    public String getTeaName() {
        return TeaName;
    }

    public void setTeaName(String teaName) {
        TeaName = teaName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Integer getDepartId() {
        return DepartId;
    }

    public void setDepartId(Integer departId) {
        DepartId = departId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(String jobTitle) {
        JobTitle = jobTitle;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getXueWei() {
        return XueWei;
    }

    public void setXueWei(String xueWei) {
        XueWei = xueWei;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
