package com.xunmaw.design.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author chenchengjian
 * @Date 2023/4/8 8:51
 * @PackageName:com.example.GraduationManagement.domain
 * @ClassName: OpenAnswerStu
 * @Description: 开题答辩——教师类
 * @Version 1.0
 */
@Table( name = "tb_open_answer_tea")
public class OpenAnswerTea implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC",strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer open_answer_id;
    private Integer tea_id;
    private Integer major_id;

    private OpenAnswer openAnswer;
    private Teacher teacher;
    private Major major;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOpen_answer_id() {
        return open_answer_id;
    }

    public void setOpen_answer_id(Integer open_answer_id) {
        this.open_answer_id = open_answer_id;
    }

    public Integer getTea_id() {
        return tea_id;
    }

    public void setTea_id(Integer tea_id) {
        this.tea_id = tea_id;
    }

    public OpenAnswer getOpenAnswer() {
        return openAnswer;
    }

    public void setOpenAnswer(OpenAnswer openAnswer) {
        this.openAnswer = openAnswer;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Integer getMajor_id() {
        return major_id;
    }

    public void setMajor_id(Integer major_id) {
        this.major_id = major_id;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    @Override
    public String toString() {
        return "OpenAnswerTea{" +
                "id=" + id +
                ", open_answer_id=" + open_answer_id +
                ", tea_id=" + tea_id +
                ", major_id=" + major_id +
                ", openAnswer=" + openAnswer +
                ", teacher=" + teacher +
                ", major=" + major +
                '}';
    }
}
