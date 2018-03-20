package com.fzm.entity;

import java.util.Date;

public class BorrowVO {
		private int borId;//借款编号
		private double borrow_interest;//利率
		private int borrow_uid;//借款人id
		private String borDate;//借款人申请时间
		private double borMoney;//借款金额
		private String borUse;//借款用途
		private int borState;//借款状态
		private int borDeadline;//借款期限
		private String borrow_instalment;//是否分期
		private Date borrow_StartDate;//起息日
		private String borrow_mortgageNo;//合同抵押编号
		private double borrow_almoney;//已还金额
		private int borrow_produce_id;//关联表id
		private String produce_id;//产品编号
		private String borrow_name;//名字
		private String credit;//信用等级
		private double borrow_repayMoney;//还款金额
		
		public String getCredit() {
			return credit;
		}
		public void setCredit(String credit) {
			this.credit = credit;
		}
		public int getBorId() {
			return borId;
		}
		public void setBorId(int borId) {
			this.borId = borId;	
		}
		public double getBorrow_interest() {
			return borrow_interest;
		}
		public void setBorrow_interest(double borrow_interest) {
			this.borrow_interest = borrow_interest;
		}
		public int getBorrow_uid() {
			return borrow_uid;
		}
		public void setBorrow_uid(int borrow_uid) {
			this.borrow_uid = borrow_uid;
		}
		public String getBorDate() {
			return borDate;
		}
		public void setBorDate(String borDate) {
			this.borDate = borDate;
		}
		public double getBorMoney() {
			return borMoney;
		}
		public void setBorMoney(double borMoney) {
			this.borMoney = borMoney;
		}
		public String getBorUse() {
			return borUse;
		}
		public void setBorUse(String borUse) {
			this.borUse = borUse;
		}
		public int getBorState() {
			return borState;
		}
		public void setBorState(int borState) {
			this.borState = borState;
		}
		public int getBorDeadline() {
			return borDeadline;
		}
		public void setBorDeadline(int borDeadline) {
			this.borDeadline = borDeadline;
		}
		public String getBorrow_instalment() {
			return borrow_instalment;
		}
		public void setBorrow_instalment(String borrow_instalment) {
			this.borrow_instalment = borrow_instalment;
		}
		public Date getBorrow_StartDate() {
			return borrow_StartDate;
		}
		public void setBorrow_StartDate(Date borrow_StartDate) {
			this.borrow_StartDate = borrow_StartDate;
		}
		public String getBorrow_mortgageNo() {
			return borrow_mortgageNo;
		}
		public void setBorrow_mortgageNo(String borrow_mortgageNo) {
			this.borrow_mortgageNo = borrow_mortgageNo;
		}
		public double getBorrow_almoney() {
			return borrow_almoney;
		}
		public void setBorrow_almoney(double borrow_almoney) {
			this.borrow_almoney = borrow_almoney;
		}
		public int getBorrow_produce_id() {
			return borrow_produce_id;
		}
		public void setBorrow_produce_id(int borrow_produce_id) {
			this.borrow_produce_id = borrow_produce_id;
		}
		public String getProduce_id() {
			return produce_id;
		}
		public void setProduce_id(String produce_id) {
			this.produce_id = produce_id;
		}
		public String getBorrow_name() {
			return borrow_name;
		}
		public void setBorrow_name(String borrow_name) {
			this.borrow_name = borrow_name;
		}
		public double getBorrow_repayMoney() {
			return borrow_repayMoney;
		}
		public void setBorrow_repayMoney(double borrow_repayMoney) {
			this.borrow_repayMoney = borrow_repayMoney;
		}
		
	}

