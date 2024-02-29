package com.xunmaw.design.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @Author it08
 * @Date 2023/2/27 9:19
 * @Version 1.0
 * 解释：文件——任务书类
 */

@Table(name = "tb_assignment_book")
public class Assignment_book implements Serializable {
    @Id
    private Integer id;
    private Integer stu_id;  //学生ID
    private Integer tea_id;  //教师ID
    private String title;   //标题
    private String foreign_title;   //  外文标题
    @Transient
    private String department;      //所属部门
    private String research_contents;   //研究内容
    private String purpose;         //研究目的
    private String reference;       //参考文献
    @Transient
    private String file_location;   //文件位置
    @Transient
    private String tea_name;   //教生姓名
    @Transient
    private String stu_name;   //学生姓名
    private Integer progress;        //审核进度
    private String appraise;        //评价
    private String sub_time;        //提交时间
    private String exam_time;       //审核时间

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer ID) {
        this.id = ID;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getForeign_title() {
        return foreign_title;
    }

    public void setForeign_title(String foreign_title) {
        this.foreign_title = foreign_title;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getResearch_contents() {
        return research_contents;
    }

    public void setResearch_contents(String research_contents) {
        this.research_contents = research_contents;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getFile_location() {
        return file_location;
    }

    public void setFile_location(String file_location) {
        this.file_location = file_location;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public String getAppraise() {
        return appraise;
    }

    public void setAppraise(String appraise) {
        this.appraise = appraise;
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
        return "Assignment_book{" +
                "id=" + id +
                ", stu_id=" + stu_id +
                ", tea_id=" + tea_id +
                ", title='" + title + '\'' +
                ", foreign_title='" + foreign_title + '\'' +
                ", department='" + department + '\'' +
                ", research_contents='" + research_contents + '\'' +
                ", purpose='" + purpose + '\'' +
                ", reference='" + reference + '\'' +
                ", file_location='" + file_location + '\'' +
                ", progress=" + progress +
                ", appraise='" + appraise + '\'' +
                ", sub_time='" + sub_time + '\'' +
                ", exam_time='" + exam_time + '\'' +
                '}';
    }
}
