package com.xunmaw.design.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author chenchengjian
 * @date 2022/3/11 21:28
 * Description:教师指导专业
 */

@Table(name = "tb_teacher_major")
public class TeaGuideMajor implements Serializable {

    @Id
    private Integer guide_major_id;//指导专业记录id
    private Integer major_id;
    private Integer teacher_id;
    private Teacher teacher;

    public Integer getGuide_major_id() {
        return guide_major_id;
    }

    public void setGuide_major_id(Integer guide_major_id) {
        this.guide_major_id = guide_major_id;
    }

    public Integer getMajor_id() {
        return major_id;
    }

    public void setMajor_id(Integer major_id) {
        this.major_id = major_id;
    }

    public Integer getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(Integer teacher_id) {
        this.teacher_id = teacher_id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "TeaGuideMajor{" +
                "guide_major_id=" + guide_major_id +
                ", major_id=" + major_id +
                ", teacher_id=" + teacher_id +
                '}';
    }
}
