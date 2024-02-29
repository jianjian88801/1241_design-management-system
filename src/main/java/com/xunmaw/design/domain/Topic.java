package com.xunmaw.design.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author chenchengjian
 * @date 2022/2/25 16:43
 * Description:选题类
 */
@Table(name = "tb_topic")
public class Topic  implements Serializable {
    @Id
    private Integer topic_id;
    private String title;
    private String cate;
    private String source;
    private String type;
    private String content;
    private Integer teacher_id;
    private Date addtime;
    private Integer student_id;
    private Integer student_major_id;
    private Integer grade;
    private Integer depart_id;
    private Integer status;
    @Transient
    private Integer general_status;
    @Transient
    private Progress progress;

    private Teacher teacher;
    private Student student;
    private List<Choose> chooses;
    private Topic_Major topic_major;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getGeneral_status() {
        return general_status;
    }

    public void setGeneral_status(Integer general_status) {
        this.general_status = general_status;
    }

    public Topic_Major getTopic_major() {
        return topic_major;
    }

    public void setTopic_major(Topic_Major topic_major) {
        this.topic_major = topic_major;
    }

    public List<Choose> getChooses() {
        return chooses;
    }

    public void setChooses(List<Choose> chooses) {
        this.chooses = chooses;
    }

    private List<Topic_Major> topic_majors;//限选专业列表

    public List<Topic_Major> getTopic_majors() {
        return topic_majors;
    }

    public void setTopic_majors(List<Topic_Major> topic_majors) {
        this.topic_majors = topic_majors;
    }

    public Integer getDepart_id() {
        return depart_id;
    }

    public void setDepart_id(Integer depart_id) {
        this.depart_id = depart_id;
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

    public Integer getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(Integer topic_id) {
        this.topic_id = topic_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setTyp(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(Integer teacher_id) {
        this.teacher_id = teacher_id;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Integer getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    public Integer getStudent_major_id() {
        return student_major_id;
    }

    public void setStudent_major_id(Integer student_major_id) {
        this.student_major_id = student_major_id;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Progress getProgress() {
        return progress;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "topic_id=" + topic_id +
                ", title='" + title + '\'' +
                ", cate='" + cate + '\'' +
                ", source='" + source + '\'' +
                ", type='" + type + '\'' +
//                ", \n   content='" + content + '\'' +"\n"+
                ", teacher_id=" + teacher_id +
                ", addtime='" + addtime + '\'' +
                ", student_id=" + student_id +
                ", student_major_id=" + student_major_id +
                ", depart_id=" + depart_id +
                ", grade=" + grade +
                ", status=" + status +
                ", general_status=" + general_status +
                ", \n   teacher=" + teacher +
                ", \n   student=" + student +
                ", \n   topic_major=" + topic_major +
                ", \n   topic_majors=" + topic_majors +
                ", \n   chooses=" + chooses +
                ", \n   progress=" + progress +
                '}';
    }
}
