package top.atstudy.specification.dto;

import top.atstudy.specification.enums.dto.EnumDeleted;

import java.io.Serializable;
import java.util.Date;

public class BaseSpecFields implements Serializable {
    private Integer displayOrder;
    private Integer version;
    private EnumDeleted deleted;
    private Long createUserId;
    private String createUserName;
    private Date createTime;
    private Long updateUserId;
    private String updateUserName;
    private Date updateTime;
    private Date lastUpdate;
    private String extras;
    private String rid;

    public BaseSpecFields() {
    }

    public Integer getDisplayOrder() {
        return this.displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public EnumDeleted getDeleted() {
        return this.deleted;
    }

    public void setDeleted(EnumDeleted deleted) {
        this.deleted = deleted;
    }

    public Long getCreateUserId() {
        return this.createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return this.createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateUserId() {
        return this.updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return this.updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getLastUpdate() {
        return this.lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getExtras() {
        return this.extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    public String getRid() {
        return this.rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }
}
