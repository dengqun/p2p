package com.fzm.entity;

/**
 * FileName: recordVO.java
 * @Description :TODO
 * @author 创建人 王斌
 * @date 创建时间 2017-8-10下午7:17:40
 * @version v1.0
 *
 * Modification  History:
 * Date              Author           Version        
 * -------------------------------------------------
 * 2017-8-10           王斌                                          @version          
 *
 * Why & What is modified:
 */
public class RecordVO {

	private int rid;//资金流水
	private int uid;//金额
	private String rechargeDate;//申请时间
	private String fulfilDate;//完成时间
	private double rMoney;//金额
	private String action;//操作状态
	private String type;//类型
	private String bankNo;//银行卡号
	private String fid;
	private String status;//状态
	private String hash;
	
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getRechargeDate() {
		return rechargeDate;
	}
	public void setRechargeDate(String rechargeDate) {
		this.rechargeDate = rechargeDate;
	}
	public String getFulfilDate() {
		return fulfilDate;
	}
	public void setFulfilDate(String fulfilDate) {
		this.fulfilDate = fulfilDate;
	}
	public double getrMoney() {
		return rMoney;
	}
	public void setrMoney(double rMoney) {
		this.rMoney = rMoney;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	
}
