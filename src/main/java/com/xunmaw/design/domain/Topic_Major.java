package com.xunmaw.design.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author chenchengjian
 * @date 2022/3/28 20:13
 * Description: 专业选题类
 */

@Table(name = "tb_topic_major")
public class Topic_Major implements Serializable {
    @Id
    private Integer id ;
    private Integer topic_id;
    private Integer major_id;
    private Integer status;
    private String  pbz;

    private Topic topic;
    private Major major;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(Integer topic_id) {
        this.topic_id = topic_id;
    }

    public Integer getMajor_id() {
        return major_id;
    }

    public void setMajor_id(Integer major_id) {
        this.major_id = major_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPbz() {
        return pbz;
    }

    public void setPbz(String pbz) {
        this.pbz = pbz;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    @Override
    public String toString() {
        return "Topic_Major{" +
                "id=" + id +
                ", topic_id=" + topic_id +
                ", major_id=" + major_id +
                ", status=" + status +
                ", pbz='" + pbz + '\'' +
                ", \n   topic=" + topic +
                ", major=" + major +
                '}';
    }
}
