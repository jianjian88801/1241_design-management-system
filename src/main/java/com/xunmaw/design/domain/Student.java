package com.xunmaw.design.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * @date 2022/1/21 20:23
 * Description: 学生类
 */

@Table(name = "tb_student")
public class Student implements Serializable{

    @Id
    private Integer id;
    private String StuNumber;//学生学号
    private String StuName;
    private Integer StuDepartId;
    private String ProNumber;
    private String StuClass;
    private String phoneNumber;
    private String campus;
    private Integer gender;
    private Integer session;//届数
    private Integer state;
    private String Password;
    private Integer choose_status;
    private String email;

    private Depart depart;
    private Major major;
    private Class aClass;
    private FileData fileData;
    private Teacher Guid_tea; //指导老师
    private OpenAnswerRecord openAnswerRecord;

    private Topic topic;

    public OpenAnswerRecord getOpenAnswerRecord() {
        return openAnswerRecord;
    }

    public void setOpenAnswerRecord(OpenAnswerRecord openAnswerRecord) {
        this.openAnswerRecord = openAnswerRecord;
    }

    public Teacher getGuid_tea() {
        return Guid_tea;
    }

    public void setGuid_tea(Teacher guid_tea) {
        Guid_tea = guid_tea;
    }

    public FileData getFileData() {
        return fileData;
    }

    public void setFileData(FileData fileData) {
        this.fileData = fileData;
    }

    public Integer getChoose_status() {
        return choose_status;
    }

    public void setChoose_status(Integer choose_status) {
        this.choose_status = choose_status;
    }

    public Student(Integer student_id) {
    }

    public Student() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStuNumber() {
        return StuNumber;
    }

    public void setStuNumber(String stuNumber) {
        StuNumber = stuNumber;
    }

    public String getStuName() {
        return StuName;
    }

    public void setStuName(String stuName) {
        StuName = stuName;
    }

    public Integer getStuDepartId() {
        return StuDepartId;
    }

    public void setStuDepartId(Integer stuDepartId) {
        StuDepartId = stuDepartId;
    }

    public String getProNumber() {
        return ProNumber;
    }

    public void setProNumber(String proNumber) {
        ProNumber = proNumber;
    }

    public String getStuClass() {
        return StuClass;
    }

    public void setStuClass(String stuClass) {
        StuClass = stuClass;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getSession() {
        return session;
    }

    public void setSession(Integer session) {
        this.session = session;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Depart getDepart() {
        return depart;
    }

    public void setDepart(Depart depart) {
        this.depart = depart;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) && Objects.equals(StuNumber, student.StuNumber) && Objects.equals(StuName, student.StuName) && Objects.equals(StuDepartId, student.StuDepartId) && Objects.equals(ProNumber, student.ProNumber) && Objects.equals(StuClass, student.StuClass) && Objects.equals(phoneNumber, student.phoneNumber) && Objects.equals(campus, student.campus) && Objects.equals(gender, student.gender) && Objects.equals(session, student.session) && Objects.equals(state, student.state) && Objects.equals(Password, student.Password) && Objects.equals(choose_status, student.choose_status) && Objects.equals(email, student.email) && Objects.equals(depart, student.depart) && Objects.equals(major, student.major) && Objects.equals(aClass, student.aClass) && Objects.equals(fileData, student.fileData) && Objects.equals(Guid_tea, student.Guid_tea) && Objects.equals(topic, student.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, StuNumber, StuName, StuDepartId, ProNumber, StuClass, phoneNumber, campus, gender, session, state, Password, choose_status, email, depart, major, aClass, fileData, Guid_tea, topic);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", StuNumber='" + StuNumber + '\'' +
                ", StuName='" + StuName + '\'' +
                ", StuDepartId=" + StuDepartId +
                ", ProNumber='" + ProNumber + '\'' +
                ", StuClass='" + StuClass + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", campus='" + campus + '\'' +
                ", topic='" + topic + '\'' +
                ", gender=" + gender +
                ", Password=" + Password +
                ", session=" + session +
                ", state=" + state +
                ", choose_status=" + choose_status +
                ", fileData=" + fileData +
                ", \n   openAnswerRecord=" + openAnswerRecord +
                ", \n   Guid_tea=" + Guid_tea +
                ", \n   depart=" + depart +
                ", \n   major=" + major +
                ", \n   aClass=" + aClass +
                '}';
    }
}
