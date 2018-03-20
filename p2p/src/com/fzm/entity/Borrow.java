package com.fzm.entity;

import java.util.Date;

import com.fzm.tools.Page;
/**
 * 
 * @Description : 借款表类
 * @author sdy
 * @date 2017-08-01
 * 
 */
public class Borrow extends Page{
		private int borId;	                //借条id
		private double borrow_interest;		//利率
		private int borrow_uid;				//用户Id	
		private Date borDate;				//申请日期
		private double borMoney;			//借款金额	
		private String borUse;				//借款用途	
		private int borState;		        //借款状态
		private int borDeadline;	        //还款日期，借款期限
		private String borrow_instalment;	//是否分期
		private Date borrow_startDate;		//起息日
		private Date borrow_dateDue;        //到息日
		private String borrow_mortgageNo;	//合同抵押编号
		private String borrow_name;			//借款人名称
		private String borrow_repaymodel;	//还款方式
		private double borrow_raiseprocess;	//筹款进度
		private double borrow_repayMoney;	//还款金额
		private double borrow_almoney;		//已还金额
		private String producet_id;			//债券包编号
		private String hash;
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
		public Date getBorDate() {
			return borDate;
		}
		public void setBorDate(Date borDate) {
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
		public Date getBorrow_startDate() {
			return borrow_startDate;
		}
		public void setBorrow_startDate(Date borrow_startDate) {
			this.borrow_startDate = borrow_startDate;
		}
		public Date getBorrow_dateDue() {
			return borrow_dateDue;
		}
		public void setBorrow_dateDue(Date borrow_dateDue) {
			this.borrow_dateDue = borrow_dateDue;
		}
		public String getBorrow_mortgageNo() {
			return borrow_mortgageNo;
		}
		public void setBorrow_mortgageNo(String borrow_mortgageNo) {
			this.borrow_mortgageNo = borrow_mortgageNo;
		}
		public String getBorrow_name() {
			return borrow_name;
		}
		public void setBorrow_name(String borrow_name) {
			this.borrow_name = borrow_name;
		}
		public String getBorrow_repaymodel() {
			return borrow_repaymodel;
		}
		public void setBorrow_repaymodel(String borrow_repaymodel) {
			this.borrow_repaymodel = borrow_repaymodel;
		}
		public double getBorrow_raiseprocess() {
			return borrow_raiseprocess;
		}
		public void setBorrow_raiseprocess(double borrow_raiseprocess) {
			this.borrow_raiseprocess = borrow_raiseprocess;
		}
		public double getBorrow_repayMoney() {
			return borrow_repayMoney;
		}
		public void setBorrow_repayMoney(double borrow_repayMoney) {
			this.borrow_repayMoney = borrow_repayMoney;
		}
		public double getBorrow_almoney() {
			return borrow_almoney;
		}
		public void setBorrow_almoney(double borrow_almoney) {
			this.borrow_almoney = borrow_almoney;
		}
		public String getProducet_id() {
			return producet_id;
		}
		public void setProducet_id(String producet_id) {
			this.producet_id = producet_id;
		}
		public String getHash() {
			return hash;
		}
		public void setHash(String hash) {
			this.hash = hash;
		}		        
		
		
}
