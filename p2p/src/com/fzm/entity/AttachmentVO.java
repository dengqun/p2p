package com.fzm.entity;

import java.util.Date;

public class AttachmentVO {
private int id;
private String type;
private String url;
private String name;
private String relationid;
private String descri;
private String createuser;
private String modifyuser;
private Date createtime;
private Date modifytime;
private String status;
private String ordernum;
private String attachmentid;

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getUrl() {
	return url;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public void setUrl(String url) {
	this.url = url;
}
public String getRelationid() {
	return relationid;
}
public void setRelationid(String relationid) {
	this.relationid = relationid;
}
public String getDescri() {
	return descri;
}
public void setDescri(String descri) {
	this.descri = descri;
}
public String getCreateuser() {
	return createuser;
}
public void setCreateuser(String createuser) {
	this.createuser = createuser;
}
public String getModifyuser() {
	return modifyuser;
}
public void setModifyuser(String modifyuser) {
	this.modifyuser = modifyuser;
}
public Date getCreatetime() {
	return createtime;
}
public void setCreatetime(Date createtime) {
	this.createtime = createtime;
}
public Date getModifytime() {
	return modifytime;
}
public void setModifytime(Date modifytime) {
	this.modifytime = modifytime;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getOrdernum() {
	return ordernum;
}
public void setOrdernum(String ordernum) {
	this.ordernum = ordernum;
}
public String getAttachmentid() {
	return attachmentid;
}
public void setAttachmentid(String attachmentid) {
	this.attachmentid = attachmentid;
}

}
