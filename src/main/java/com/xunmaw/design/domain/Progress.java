package com.xunmaw.design.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @Author it08
 * @Date 2023/3/16 10:18
 * @Version 1.0
 * 学生 任务进度类
 */

@Table(name = "tb_progress")
public class Progress implements Serializable {

    @Id
    private Integer id;
    private Integer stu_id;
    private Integer tea_id;
    private Integer progress;

    @Transient
    private Student student;
    @Transient
    private Teacher teacher;

    public Progress(Integer stu_id) {
        this.stu_id = stu_id;
    }

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

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
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
        return "Progress{" +
                "id=" + id +
                ", stu_id=" + stu_id +
                ", tea_id=" + tea_id +
                ", progress=" + progress +
                ", student=" + student +
                ", teacher=" + teacher +
                '}';
    }
}
