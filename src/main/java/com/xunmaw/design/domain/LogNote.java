package com.xunmaw.design.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @Author it08
 * @Date 2023/3/16 11:44
 * @Version 1.0
 * 日志记录类
 */
@Table(name = "tb_log_note")
public class LogNote implements Serializable {

    @Id
    private Integer id;
    private Integer stu_id;
    private Integer tea_id;
    private String typename;
    private String details;
    private String date;

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

    public Integer getStu_id() {
        return stu_id;
    }

    public void setStu_id(Integer stu_id) {
        this.stu_id = stu_id;
    }

    public Integer getTea_id() {
        return tea_id;
    }

    public void setTea_id(Integer tea_id) {
        this.tea_id = tea_id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
        return "LogNote{" +
                "id=" + id +
                ", stu_id=" + stu_id +
                ", tea_id=" + tea_id +
                ", typename='" + typename + '\'' +
                ", details='" + details + '\'' +
                ", date='" + date + '\'' +
                ", student=" + student +
                ", teacher=" + teacher +
                '}';
    }
}
