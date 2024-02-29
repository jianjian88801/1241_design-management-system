package com.xunmaw.design.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @Author chenchengjian
 * @Date 2023/4/6 14:51
 * @PackageName:com.example.GraduationManagement.domain
 * @ClassName: OpenAnswer
 * @Description: 开题答辩类
 * @Version 1.0
 */
@Table(name = "tb_open_answer")
public class OpenAnswer implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC",strategy = GenerationType.IDENTITY)
    private Integer id;
    private String group_name;  //组别名称
    private Integer headman_id; //组长教师id
    private Integer session;
    private Integer major_id;
    private String address;
    private String time;

    @Transient
    private Integer stu_num;
    @Transient
    private Integer tea_num;
    @Transient
    private Major major;

    public Integer getStu_num() {
        return stu_num;
    }

    public void setStu_num(Integer stu_num) {
        this.stu_num = stu_num;
    }

    public Integer getTea_num() {
        return tea_num;
    }

    public void setTea_num(Integer tea_num) {
        this.tea_num = tea_num;
    }

    private Teacher headMan;    //组长

    private List<Teacher> teacherList;

    private List<Student> studentList;

    private List<Integer> NoStudentIdList;

    public List<Integer> getNoStudentIdList() {
        return NoStudentIdList;
    }

    public void setNoStudentIdList(List<Integer> noStudentIdList) {
        NoStudentIdList = noStudentIdList;
    }

    public List<Teacher> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public Integer getHeadman_id() {
        return headman_id;
    }

    public void setHeadman_id(Integer headman_id) {
        this.headman_id = headman_id;
    }

    public Integer getSession() {
        return session;
    }

    public void setSession(Integer session) {
        this.session = session;
    }

    public Teacher getHeadMan() {
        return headMan;
    }

    public void setHeadMan(Teacher headMan) {
        this.headMan = headMan;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "OpenAnswer{" +
                "id=" + id +
                ", group_name='" + group_name + '\'' +
                ", headman_id=" + headman_id +
                ", session=" + session +
                ", major_id=" + major_id +
                ", address='" + address + '\'' +
                ", time='" + time + '\'' +
                ", stu_num=" + stu_num +
                ", tea_num=" + tea_num +
                ", major=" + major +
                ", headMan=" + headMan +
                ", \n   teacherList=" + teacherList +
                ", \n   studentList=" + studentList +
                ", \n   NoStudentIdList=" + NoStudentIdList +
                '}';
    }
}
