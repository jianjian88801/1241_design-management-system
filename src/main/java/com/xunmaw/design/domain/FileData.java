package com.xunmaw.design.domain;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "filedata")
public class FileData {

    @Id
    private Integer id;
    private String studentid;
    private String shufile; // 文献论述
    private String shutime;
    private String jobfile; // 实习报告
    private String jobtime;
    private String ktfile; // 开题报告
    private String kttime;
    private String bsfile; //毕业论文
    private String bstime;
    private String yifile; // 外文翻译
    private String yitime;
    private String chafile; // 查重报告
    private String chatime;
    private String xgfile; // 相关文件
    private String xgtime;

    private String zqfile; // 中期检查
    private String zqtime;

    public FileData(String stuNumber) {
    }

    public FileData() {

    }



    public FileData(FileData fileData) {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getShufile() {
        return shufile;
    }

    public void setShufile(String shufile) {
        this.shufile = shufile;
    }

    public String getShutime() {
        return shutime;
    }

    public void setShutime(String shutime) {
        this.shutime = shutime;
    }

    public String getJobfile() {
        return jobfile;
    }

    public void setJobfile(String jobfile) {
        this.jobfile = jobfile;
    }

    public String getJobtime() {
        return jobtime;
    }

    public void setJobtime(String jobtime) {
        this.jobtime = jobtime;
    }

    public String getKtfile() {
        return ktfile;
    }

    public void setKtfile(String ktfile) {
        this.ktfile = ktfile;
    }

    public String getKttime() {
        return kttime;
    }

    public void setKttime(String kttime) {
        this.kttime = kttime;
    }

    public String getBsfile() {
        return bsfile;
    }

    public void setBsfile(String bsfile) {
        this.bsfile = bsfile;
    }

    public String getBstime() {
        return bstime;
    }

    public void setBstime(String bstime) {
        this.bstime = bstime;
    }

    public String getYifile() {
        return yifile;
    }

    public void setYifile(String yifile) {
        this.yifile = yifile;
    }

    public String getYitime() {
        return yitime;
    }

    public void setYitime(String yitime) {
        this.yitime = yitime;
    }

    public String getChafile() {
        return chafile;
    }

    public void setChafile(String chafile) {
        this.chafile = chafile;
    }

    public String getChatime() {
        return chatime;
    }

    public void setChatime(String chatime) {
        this.chatime = chatime;
    }

    public String getXgfile() {
        return xgfile;
    }

    public void setXgfile(String xgfile) {
        this.xgfile = xgfile;
    }

    public String getXgtime() {
        return xgtime;
    }

    public void setXgtime(String xgtime) {
        this.xgtime = xgtime;
    }

    public String getZqfile() {
        return zqfile;
    }

    public void setZqfile(String zqfile) {
        this.zqfile = zqfile;
    }

    public String getZqtime() {
        return zqtime;
    }

    public void setZqtime(String zqtime) {
        this.zqtime = zqtime;
    }

    @Override
    public String toString() {
        return "FileData{" +
                "id=" + id +
                ", studentid='" + studentid + '\'' +
                ", shufile='" + shufile + '\'' +
                ", shutime='" + shutime + '\'' +
                ", jobfile='" + jobfile + '\'' +
                ", jobtime='" + jobtime + '\'' +
                ", ktfile='" + ktfile + '\'' +
                ", kttime='" + kttime + '\'' +
                ", bsfile='" + bsfile + '\'' +
                ", bstime='" + bstime + '\'' +
                ", yifile='" + yifile + '\'' +
                ", yitime='" + yitime + '\'' +
                ", chafile='" + chafile + '\'' +
                ", chatime='" + chatime + '\'' +
                ", xgfile='" + xgfile + '\'' +
                ", xgtime='" + xgtime + '\'' +
                ", zqfile='" + zqfile + '\'' +
                ", zqtime='" + zqtime + '\'' +
                '}';
    }
}
