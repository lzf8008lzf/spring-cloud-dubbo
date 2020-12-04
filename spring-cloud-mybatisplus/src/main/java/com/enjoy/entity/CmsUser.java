package com.enjoy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author LiZhaofu
 * @since 2020-06-01
 */
public class CmsUser extends Model<CmsUser> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 唯一标识
     */
    private String uuid;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 标签ID
     */
    private String tagId;

    /**
     * 创建日期
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createDate;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 最后修改时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime modifyDate;

    /**
     * 环信ID
     */
    private String huanxinId;

    /**
     * 机构ID
     */
    private Integer orgId;

    /**
     * 关注用户列表
     */
    private String attentionAssociatorId;

    /**
     * 粉丝用户列表
     */
    private String fansAssociatorId;

    /**
     * 描述
     */
    private String description;

    /**
     * 已禁用
     */
    private Boolean disabled;

    /**
     * 0普通用户；1机构用户；2僵尸用户
     */
    private Integer orgType;

    /**
     * 粉丝总数（目前只有机构用到）
     */
    private Integer fansCount;

    /**
     * 手机uuid
     */
    private String clientUuid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getHuanxinId() {
        return huanxinId;
    }

    public void setHuanxinId(String huanxinId) {
        this.huanxinId = huanxinId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getAttentionAssociatorId() {
        return attentionAssociatorId;
    }

    public void setAttentionAssociatorId(String attentionAssociatorId) {
        this.attentionAssociatorId = attentionAssociatorId;
    }

    public String getFansAssociatorId() {
        return fansAssociatorId;
    }

    public void setFansAssociatorId(String fansAssociatorId) {
        this.fansAssociatorId = fansAssociatorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Integer getOrgType() {
        return orgType;
    }

    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }

    public Integer getFansCount() {
        return fansCount;
    }

    public void setFansCount(Integer fansCount) {
        this.fansCount = fansCount;
    }

    public String getClientUuid() {
        return clientUuid;
    }

    public void setClientUuid(String clientUuid) {
        this.clientUuid = clientUuid;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CmsUser{" +
        "id=" + id +
        ", uuid=" + uuid +
        ", nickName=" + nickName +
        ", tagId=" + tagId +
        ", createDate=" + createDate +
        ", avatar=" + avatar +
        ", modifyDate=" + modifyDate +
        ", huanxinId=" + huanxinId +
        ", orgId=" + orgId +
        ", attentionAssociatorId=" + attentionAssociatorId +
        ", fansAssociatorId=" + fansAssociatorId +
        ", description=" + description +
        ", disabled=" + disabled +
        ", orgType=" + orgType +
        ", fansCount=" + fansCount +
        ", clientUuid=" + clientUuid +
        "}";
    }
}
