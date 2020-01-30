package com.williams.appraisalcompany.model.Entity;

import com.williams.appraisalcompany.model.Constant.Status;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Token implements Serializable {

    private int id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp expiresAt;
    private Status status;
    private String token;
    private String user;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "created_at", nullable = false)
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "updated_at", nullable = true)
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Basic
    @Column(name = "expires_at", nullable = true)
    public Timestamp getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Timestamp expiresAt) {
        this.expiresAt = expiresAt;
    }

    @Basic
    @Column(name = "status", nullable = true, length = 16)
    @Enumerated(EnumType.STRING)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Basic
    @Column(name = "token", nullable = false, length = 64)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @PrePersist
    public void beforeSave() {
        this.createdAt = new Timestamp(new Date().getTime());
    }

    @PreUpdate
    private void beforeUpdate() {
        this.updatedAt = new Timestamp(new Date().getTime());
    }

    @Basic
    @Column(name = "user", nullable = false, length = 32)
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Token token = (Token) o;

        if (id != token.id) {
            return false;
        }
        if (createdAt != null ? !createdAt.equals(token.createdAt) : token.createdAt != null) {
            return false;
        }
        if (updatedAt != null ? !updatedAt.equals(token.updatedAt) : token.updatedAt != null) {
            return false;
        }
        if (expiresAt != null ? !expiresAt.equals(token.expiresAt) : token.expiresAt != null) {
            return false;
        }
        if (status != token.status) {
            return false;
        }
        if (token != null ? !token.equals(token.token) : token.token != null) {
            return false;
        }
        return user != null ? user.equals(token.user) : token.user == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (expiresAt != null ? expiresAt.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

}
