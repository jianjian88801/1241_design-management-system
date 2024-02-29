package com.xunmaw.design.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author chenchengjian
 * @date 2022/2/21 16:58
 * Description:班级类
 */
@Table(name = "tb_class")
public class Class implements Serializable {
    @Id
    private Integer ClassId;
    private String ClassChar;
    private String ClassName;
    private Integer majorId;
    private Integer session;

    public Class() {
    }

    public Class(String classChar) {
        ClassChar = classChar;
    }

    public Integer getClassId() {
        return ClassId;
    }

    public void setClassId(Integer classId) {
        ClassId = classId;
    }

    public String getClassChar() {
        return ClassChar;
    }

    public void setClassChar(String classChar) {
        ClassChar = classChar;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    public Integer getSession() {
        return session;
    }

    public void setSession(Integer session) {
        this.session = session;
    }

    @Override
    public String toString() {
        return "Class{" +
                "ClassId=" + ClassId +
                ", ClassChar='" + ClassChar + '\'' +
                ", ClassName='" + ClassName + '\'' +
                ", majorId=" + majorId +
                ", session=" + session +
                '}';
    }
}
