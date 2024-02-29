package com.xunmaw.design.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * @Author chenchengjian
 * @Date 2023/4/12 10:35
 * @PackageName:com.example.GraduationManagement.domain
 * @ClassName: OpenAnswerRecord
 * @Description: 开题答辩记录类
 * @Version 1.0
 */
@Table( name = "tb_open_answer_record")
public class OpenAnswerRecord implements Serializable {
    @Id
    private Integer id;
    private Integer open_answer_id;
    private Integer stu_id;
    private String result;
    private String remark;
    private String attitude;
    private String collecting_status;
    private String understanding_status;
    private String open_report_status;
    private String sub_time;
    private String exam_time;
    private Integer progress;

    private OpenAnswer openAnswer;
    private Student student;
    private List<OpenAnswerQuestion> openAnswerQuestionList;

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAttitude() {
        return attitude;
    }

    public void setAttitude(String attitude) {
        this.attitude = attitude;
    }

    public String getCollecting_status() {
        return collecting_status;
    }

    public void setCollecting_status(String collecting_status) {
        this.collecting_status = collecting_status;
    }

    public String getUnderstanding_status() {
        return understanding_status;
    }

    public void setUnderstanding_status(String understanding_status) {
        this.understanding_status = understanding_status;
    }

    public String getOpen_report_status() {
        return open_report_status;
    }

    public void setOpen_report_status(String open_report_status) {
        this.open_report_status = open_report_status;
    }

    public String getSub_time() {
        return sub_time;
    }

    public void setSub_time(String sub_time) {
        this.sub_time = sub_time;
    }

    public String getExam_time() {
        return exam_time;
    }

    public void setExam_time(String exam_time) {
        this.exam_time = exam_time;
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

    public List<OpenAnswerQuestion> getOpenAnswerQuestionList() {
        return openAnswerQuestionList;
    }

    public void setOpenAnswerQuestionList(List<OpenAnswerQuestion> openAnswerQuestionList) {
        this.openAnswerQuestionList = openAnswerQuestionList;
    }

    @Override
    public String toString() {
        return "OpenAnswerRecord{" +
                "id=" + id +
                ", open_answer_id=" + open_answer_id +
                ", stu_id=" + stu_id +
                ", result='" + result + '\'' +
                ", remark='" + remark + '\'' +
                ", attitude='" + attitude + '\'' +
                ", collecting_status='" + collecting_status + '\'' +
                ", understanding_status='" + understanding_status + '\'' +
                ", open_report_status='" + open_report_status + '\'' +
                ", sub_time='" + sub_time + '\'' +
                ", exam_time='" + exam_time + '\'' +
                ", progress=" + progress +
                ", openAnswer=" + openAnswer +
                ", student=" + student +
                ", openAnswerQuestionList=" + openAnswerQuestionList +
                '}';
    }
}
