package com.xunmaw.design.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @Author it08
 * @Date 2023/3/21 10:09
 * @Version 1.0
 * 教师指标类
 */
@Table(name = "tb_tea_target")
public class TeaTarget implements Serializable {

    @Id
    private Integer id;
    private Integer tea_id;
    private Integer stu_num;
    private Integer topic_num;
    private Integer guidance_num;
    private Integer good_stu_num;
    private Integer session;

    @Transient
    private Integer stu_num_per;    //学生——百分比
    @Transient
    private Integer topic_num_per;  //选题——百分比
    @Transient
    private Integer guidance_num_per;   //指导记录——百分比
    @Transient
    private Integer good_stu_num_per;   //推优——百分比

    public Integer getStu_num_per() {
        return stu_num_per;
    }

    public void setStu_num_per(Integer stu_num_per) {
        this.stu_num_per = stu_num_per;
    }

    public Integer getTopic_num_per() {
        return topic_num_per;
    }

    public void setTopic_num_per(Integer topic_num_per) {
        this.topic_num_per = topic_num_per;
    }

    public Integer getGuidance_num_per() {
        return guidance_num_per;
    }

    public void setGuidance_num_per(Integer guidance_num_per) {
        this.guidance_num_per = guidance_num_per;
    }

    public Integer getGood_stu_num_per() {
        return good_stu_num_per;
    }

    public void setGood_stu_num_per(Integer good_stu_num_per) {
        this.good_stu_num_per = good_stu_num_per;
    }

    public Integer getSession() {
        return session;
    }

    public void setSession(Integer session) {
        this.session = session;
    }

    private Teacher teacher;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTea_id() {
        return tea_id;
    }

    public void setTea_id(Integer tea_id) {
        this.tea_id = tea_id;
    }

    public Integer getStu_num() {
        return stu_num;
    }

    public void setStu_num(Integer stu_num) {
        this.stu_num = stu_num;
    }

    public Integer getTopic_num() {
        return topic_num;
    }

    public void setTopic_num(Integer topic_num) {
        this.topic_num = topic_num;
    }

    public Integer getGuidance_num() {
        return guidance_num;
    }

    public void setGuidance_num(Integer guidance_num) {
        this.guidance_num = guidance_num;
    }

    public Integer getGood_stu_num() {
        return good_stu_num;
    }

    public void setGood_stu_num(Integer good_stu_num) {
        this.good_stu_num = good_stu_num;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "TeaTarget{" +
                "id=" + id +
                ", tea_id=" + tea_id +
                ", stu_num=" + stu_num +
                ", topic_num=" + topic_num +
                ", guidance_num=" + guidance_num +
                ", good_stu_num=" + good_stu_num +
                ", session=" + session +
                ", stu_num_per=" + stu_num_per +
                ", topic_num_per=" + topic_num_per +
                ", guidance_num_per=" + guidance_num_per +
                ", good_stu_num_per=" + good_stu_num_per +
                ", teacher=" + teacher +
                '}';
    }
}
