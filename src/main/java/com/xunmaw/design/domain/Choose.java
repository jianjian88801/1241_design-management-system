package com.xunmaw.design.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chenchengjian
 * @date 2022/4/5 20:38
 * Description: 学生志愿选择类
 */
@Table(name = "tb_choose")
public class Choose implements Serializable {
    @Id
    private Integer choose_id ;
    private Integer topic_id;
    private Integer student_id;
    private Integer status;
    private Integer wish;
    private Date sel_time;

    private Student student;
    private Topic topic;
    private Teacher teacher;

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

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Integer getChoose_id() {
        return choose_id;
    }

    public void setChoose_id(Integer choose_id) {
        this.choose_id = choose_id;
    }

    public Integer getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(Integer topic_id) {
        this.topic_id = topic_id;
    }

    public Integer getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getWish() {
        return wish;
    }

    public void setWish(Integer wish) {
        this.wish = wish;
    }

    public Date getSel_time() {
        return sel_time;
    }

    public void setSel_time(Date sel_time) {
        this.sel_time = sel_time;
    }

    @Override
    public String toString() {
        return "Choose{" +
                "choose_id=" + choose_id +
                ", topic_id=" + topic_id +
                ", student_id=" + student_id +
                ", status=" + status +
                ", wish=" + wish +
                ", sel_time=" + sel_time +
                ", \n   student=" + student +
                ", \n   teacher=" + teacher +
                ", \n   topic=" + topic +
                '}';
    }
}
