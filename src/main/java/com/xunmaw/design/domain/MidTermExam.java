package com.xunmaw.design.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @Author chenchengjian
 * @Date 2023/4/6 10:04
 * @PackageName:com.example.GraduationManagement.domain
 * @ClassName: MidTermExam
 * @Description: 中期检查类
 * @Version 1.0
 */
@Table(name = "tb_mid_term_exam")
public class MidTermExam implements Serializable {
    @Id
    private Integer id;
    private Integer stu_id;
    private Integer tea_id;
    private String  time_schedule;  // 时间安排表
    private String completion;      // 完成情况
    private String initial_achieve; // 阶段性成果
    private String main_issues;     // 存在主要问题
    private Integer progress;       // 进度标志
    private Integer ass_book_id;    // 对应任务书id
    private String sub_time;            // 提交时间
    private String exam_time;
    private String remark;          //教师备注

    @Transient
    private String File_location;

    private Teacher teacher;

    private Student student;

    private Assignment_book assignment_book;

    public String getFile_location() {
        return File_location;
    }

    public void setFile_location(String file_location) {
        File_location = file_location;
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

    public String getTime_schedule() {
        return time_schedule;
    }

    public void setTime_schedule(String time_schedule) {
        this.time_schedule = time_schedule;
    }

    public String getCompletion() {
        return completion;
    }

    public void setCompletion(String completion) {
        this.completion = completion;
    }

    public String getInitial_achieve() {
        return initial_achieve;
    }

    public void setInitial_achieve(String initial_achieve) {
        this.initial_achieve = initial_achieve;
    }

    public String getMain_issues() {
        return main_issues;
    }

    public void setMain_issues(String main_issues) {
        this.main_issues = main_issues;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Integer getAss_book_id() {
        return ass_book_id;
    }

    public void setAss_book_id(Integer ass_book_id) {
        this.ass_book_id = ass_book_id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Assignment_book getAssignment_book() {
        return assignment_book;
    }

    public void setAssignment_book(Assignment_book assignment_book) {
        this.assignment_book = assignment_book;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    @Override
    public String toString() {
        return "MidTermExam{" +
                "id=" + id +
                ", stu_id=" + stu_id +
                ", tea_id=" + tea_id +
                ", time_schedule='" + time_schedule + '\'' +
                ", completion='" + completion + '\'' +
                ", initial_achieve='" + initial_achieve + '\'' +
                ", main_issues='" + main_issues + '\'' +
                ", progress=" + progress +
                ", ass_book_id=" + ass_book_id +
                ", sub_time='" + sub_time + '\'' +
                ", exam_time='" + exam_time + '\'' +
                ", remark='" + remark + '\'' +
                ", File_location='" + File_location + '\'' +
                ", teacher=" + teacher +
                ", student=" + student +
                ", assignment_book=" + assignment_book +
                '}';
    }
}
