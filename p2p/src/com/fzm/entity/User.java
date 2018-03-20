package com.fzm.entity;

import java.util.Date;
import java.util.List;

import com.fzm.tools.Page;
/**
 * 
 * @Description : 用户类
 * @author sdy
 * @date 2017-08-01
 * 
 */
public class User extends Page{
		private int  uid;		//用户id
		private String name;	//用户名称
		private String phone;	//用户手机
		private String email;	//用户email
		private String password;//用户密码
		private String type;	//用户类型
		private String bankNo;  //用户银行卡号
		private String idNum ;	//用户身份证号
		private double balance; //用户余额
		private String credit; // 用户信用等级
		private double creditNum;//用户可借余额
		private double borTotal; //共计借款
		private double reTotal;//代还款总金额
		private double bondHoldings;	//债权资产
		private double availableCredit;	//可用信用额
		private double totalAssets;		//总资产
		private Date regDate;			//注册时间
		private String bankPhone;		//银行预留手机号
		private String bankName;		//银行名称
		private int flag;
		private String randomNumber;
		private List<Borrow> borrow ;   //用户下的所有借款		
		public Date getRegDate() {
			return regDate;
		}
		public void setRegDate(Date regDate) {
			this.regDate = regDate;
		}
		public double getBondHoldings() {
			return bondHoldings;
		}
		public void setBondHoldings(double bondHoldings) {
			this.bondHoldings = bondHoldings;
		}
		public double getAvailableCredit() {
			return availableCredit;
		}
		public void setAvailableCredit(double availableCredit) {
			this.availableCredit = availableCredit;
		}
		public double getTotalAssets() {
			return totalAssets;
		}
		public void setTotalAssets(double totalAssets) {
			this.totalAssets = totalAssets;
		}
		public double getCreditNum() {
			return creditNum;
		}
		public void setCreditNum(double creditNum) {
			this.creditNum = creditNum;
		}
		public List<Borrow> getBorrow() {
			return borrow;
		}
		public void setBorrow(List<Borrow> borrow) {
			this.borrow = borrow;
		}
		public double getBorTotal() {
			return borTotal;
		}
		public void setBorTotal(double borTotal) {
			this.borTotal = borTotal;
		}
		public double getReTotal() {
			return reTotal;
		}
		public void setReTotal(double reTotal) {
			this.reTotal = reTotal;
		}
		public int getUid() {
			return uid;
		}
		public void setUid(int uid) {
			this.uid = uid;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
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
		public String getIdNum() {
			return idNum;
		}
		public void setIdNum(String idNum) {
			this.idNum = idNum;
		}
		public double getBalance() {
			return balance;
		}
		public void setBalance(double balance) {
			this.balance = balance;
		}
		public String getCredit() {
			return credit;
		}
		public void setCredit(String credit) {
			this.credit = credit;
		}
		public String getBankPhone() {
			return bankPhone;
		}
		public void setBankPhone(String bankPhone) {
			this.bankPhone = bankPhone;
		}
		public String getBankName() {
			return bankName;
		}
		public void setBankName(String bankName) {
			this.bankName = bankName;
		}
		/**
		 * @return the flag
		 */
		public int getFlag() {
			return flag;
		}
		/**
		 * @param flag the flag to set
		 */
		public void setFlag(int flag) {
			this.flag = flag;
		}
		/**
		 * @return the randomNumber
		 */
		public String getRandomNumber() {
			return randomNumber;
		}
		/**
		 * @param randomNumber the randomNumber to set
		 */
		public void setRandomNumber(String randomNumber) {
			this.randomNumber = randomNumber;
		}
		
}
