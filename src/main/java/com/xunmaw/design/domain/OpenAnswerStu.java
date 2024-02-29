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
 * @Description: 开题答辩——学生类
 * @Version 1.0
 */
@Table( name = "tb_open_answer_stu")
public class OpenAnswerStu implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC",strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer open_answer_id;
    private Integer stu_id;
    private Integer major_id;

    private OpenAnswer openAnswer;
    private Student student;
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

    public Integer getStu_id() {
        return stu_id;
    }

    public void setStu_id(Integer stu_id) {
        this.stu_id = stu_id;
    }

    public OpenAnswer getOpenAnswer() {
        return openAnswer;
    }

    public void setOpenAnswer(OpenAnswer openAnswer) {
        this.openAnswer = openAnswer;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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
        return "OpenAnswerStu{" +
                "id=" + id +
                ", open_answer_id=" + open_answer_id +
                ", stu_id=" + stu_id +
                ", major_id=" + major_id +
                ", openAnswer=" + openAnswer +
                ", student=" + student +
                ", major=" + major +
                '}';
    }
}
