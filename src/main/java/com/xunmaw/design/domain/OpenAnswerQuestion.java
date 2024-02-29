package com.xunmaw.design.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author chenchengjian
 * @Date 2023/4/12 10:38
 * @PackageName:com.example.GraduationManagement.domain
 * @ClassName: OpenAnswerQuestion
 * @Description: 开题答辩问题类
 * @Version 1.0
 */
@Table( name = "tb_open_answer_question")
public class OpenAnswerQuestion implements Serializable {
    @Id
    private Integer id;
    private Integer open_answer_id;
    private Integer record_id;
    private Integer stu_id;
    private Integer tea_id;
    private String question;
    private String answer;
    private String remark;

    private OpenAnswer openAnswer;
    private OpenAnswerRecord openAnswerRecord;
    private Student student;
    private Teacher teacher;


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

    public Integer getRecord_id() {
        return record_id;
    }

    public void setRecord_id(Integer record_id) {
        this.record_id = record_id;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public OpenAnswer getOpenAnswer() {
        return openAnswer;
    }

    public void setOpenAnswer(OpenAnswer openAnswer) {
        this.openAnswer = openAnswer;
    }

    public OpenAnswerRecord getOpenAnswerRecord() {
        return openAnswerRecord;
    }

    public void setOpenAnswerRecord(OpenAnswerRecord openAnswerRecord) {
        this.openAnswerRecord = openAnswerRecord;
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
        return "OpenAnswerQuestion{" +
                "id=" + id +
                ", open_answer_id=" + open_answer_id +
                ", record_id=" + record_id +
                ", stu_id=" + stu_id +
                ", tea_id=" + tea_id +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", remark='" + remark + '\'' +
                ", openAnswer=" + openAnswer +
                ", openAnswerRecord=" + openAnswerRecord +
                ", student=" + student +
                ", teacher=" + teacher +
                '}';
    }
}
