package com.williams.appraisalcompany.model.Entity;

import com.williams.appraisalcompany.model.Constant.NotificationAction;
import com.williams.appraisalcompany.model.Constant.NotificationChannel;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
public class NotificationTemplate implements Serializable {

    private Integer id;
    private NotificationAction action;
    private NotificationChannel channel;
    private String template;
    private String fields;
    private String subject;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Boolean isActive;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "action", nullable = false, length = 32)
    @Enumerated(EnumType.STRING)
    public NotificationAction getAction() {
        return action;
    }

    public void setAction(NotificationAction action) {
        this.action = action;
    }

    @Basic
    @Column(name = "channel", nullable = false, length = 32)
    @Enumerated(EnumType.STRING)
    public NotificationChannel getChannel() {
        return channel;
    }

    public void setChannel(NotificationChannel channel) {
        this.channel = channel;
    }

    @Basic
    @Column(name = "template", nullable = false)
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Basic
    @Column(name = "fields", nullable = false, length = 32)
    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    @Basic
    @Column(name = "subject", length = 32)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Basic
    @Column(name = "created_at")
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "updated_at")
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Basic
    @Column(name = "is_active")
    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }


}
