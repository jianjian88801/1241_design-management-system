package com.xunmaw.design.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @Author it08
 * @Date 2023/3/6 13:52
 * @Version 1.0
 * 开通报告类
 */
@Table (name = "opening_report")
public class Opening_report implements Serializable {
    @Id
    private Integer id;
    private Integer stu_id;         //对应学生ID
    private Integer tea_id;         //对应教师ID
    private Integer ass_book_id;    //对应任务书ID
    private String background_and_significance; //选题背景及意义
    private String development_trend;           //发展趋势
    private String reference;                   //参考文件
    private String content;         //研究内容
    private String suggestions;     //教师意见
    private String expected;        //预期结果
    private String design;          //研究思路
    private String sub_time;        //提交时间
    private String exam_time;       //审核时间
    private Integer progress;       //进度


    @Transient
    private Assignment_book assignment_book;
    @Transient
    private String File_location;
    @Transient
    private String tea_name;   //教生姓名
    @Transient
    private String stu_name;   //学生姓名

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

    public Integer getAss_book_id() {
        return ass_book_id;
    }

    public void setAss_book_id(Integer ass_book_id) {
        this.ass_book_id = ass_book_id;
    }

    public String getBackground_and_significance() {
        return background_and_significance;
    }

    public void setBackground_and_significance(String background_and_significance) {
        this.background_and_significance = background_and_significance;
    }

    public String getDevelopment_trend() {
        return development_trend;
    }

    public void setDevelopment_trend(String development_trend) {
        this.development_trend = development_trend;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public Assignment_book getAssignment_book() {
        return assignment_book;
    }

    public void setAssignment_book(Assignment_book assignment_book) {
        this.assignment_book = assignment_book;
    }

    public String getFile_location() {
        return File_location;
    }

    public void setFile_location(String file_location) {
        File_location = file_location;
    }

    public String getTea_name() {
        return tea_name;
    }

    public void setTea_name(String tea_name) {
        this.tea_name = tea_name;
    }

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
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

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "Opening_report{" +
                "id=" + id +
                ", stu_id=" + stu_id +
                ", tea_id=" + tea_id +
                ", ass_book_id=" + ass_book_id +
                ", background_and_significance='" + background_and_significance + '\'' +
                ", development_trend='" + development_trend + '\'' +
                ", reference='" + reference + '\'' +
                ", content='" + content + '\'' +
                ", suggestions='" + suggestions + '\'' +
                ", expected='" + expected + '\'' +
                ", design='" + design + '\'' +
                ", sub_time='" + sub_time + '\'' +
                ", exam_time='" + exam_time + '\'' +
                ", progress=" + progress +
                ", assignment_book=" + assignment_book +
                ", File_location='" + File_location + '\'' +
                ", tea_name='" + tea_name + '\'' +
                ", stu_name='" + stu_name + '\'' +
                '}';
    }
}
