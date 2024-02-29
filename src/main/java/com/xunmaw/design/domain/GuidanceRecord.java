package com.xunmaw.design.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @Author it08
 * @Date 2023/3/17 9:13
 * @Version 1.0
 * 指导记录--详情类
 */
@Table(name = "tb_guidance_record")
public class GuidanceRecord implements Serializable {
    @Id
    private Integer id;
    private Integer session;
    private Integer guidance_id;
    private Integer majorId;
    private Integer tea_id;
    private Integer stu_id;
    private String address;
    private String time;
    private String detail;

    @Transient
    private Major major;
    @Transient
    private Student student;
    @Transient
    private Teacher teacher;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSession() {
        return session;
    }

    public void setSession(Integer session) {
        this.session = session;
    }

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    public Integer getTea_id() {
        return tea_id;
    }

    public Integer getGuidance_id() {
        return guidance_id;
    }

    public void setGuidance_id(Integer guidance_id) {
        this.guidance_id = guidance_id;
    }

    public void setTea_id(Integer tea_id) {
        this.tea_id = tea_id;
    }

    public Integer getStu_id() {
        return stu_id;
    }

    public void setStu_id(Integer stu_id) {
        this.stu_id = stu_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "GuidanceRecord{" +
                "id=" + id +
                ", session=" + session +
                ", guidance_id=" + guidance_id +
                ", majorId=" + majorId +
                ", tea_id=" + tea_id +
                ", stu_id=" + stu_id +
                ", address='" + address + '\'' +
                ", time='" + time + '\'' +
                ", detail='" + detail + '\'' +
                ", major=" + major +
                ", student=" + student +
                ", teacher=" + teacher +
                '}';
    }
}
