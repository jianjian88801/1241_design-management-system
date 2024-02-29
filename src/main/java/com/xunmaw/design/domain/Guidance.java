package com.xunmaw.design.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author it08
 * @Date 2023/3/17 9:53
 * @Version 1.0
 * 指导记录汇总类
 */
@Table(name = "tb_guidance")
public class Guidance implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC",strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer session;
    private Integer major_id;
    private Integer teacher_id;
    private Integer guidance_count;

    @Transient
    private Major major;
    @Transient
    private Teacher teacher;

    public Integer getSession() {
        return session;
    }

    public void setSession(Integer session) {
        this.session = session;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Integer getGuidance_count() {
        return guidance_count;
    }

    public void setGuidance_count(Integer guidance_count) {
        this.guidance_count = guidance_count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMajor_id() {
        return major_id;
    }

    public void setMajor_id(Integer major_id) {
        this.major_id = major_id;
    }

    public Integer getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(Integer teacher_id) {
        this.teacher_id = teacher_id;
    }

    @Override
    public String toString() {
        return "Guidance{" +
                "id=" + id +
                ", session=" + session +
                ", major_id=" + major_id +
                ", teacher_id=" + teacher_id +
                ", guidance_count=" + guidance_count +
                ", major=" + major +
                ", teacher=" + teacher +
                '}';
    }
}
