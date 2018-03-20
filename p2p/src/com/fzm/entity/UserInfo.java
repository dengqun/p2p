/**
 * 
 */
package com.fzm.entity;

/**   
 *    
 * 项目名称：cgb_p2p   
 * 类名称：User   
 * 类描述：   
 * 创建人：maamin   
 * 创建时间：2017-8-3 下午5:07:05   
 * 修改人：maamin   
 * 修改时间：2017-8-3 下午5:07:05   
 * 修改备注：   
 * @version    
 *    
 */
public class UserInfo {
	private int uid;	//uid
	private String name; //申请人姓名
	private String idNum;	//身份证号
	private String email;	//邮箱号
	private String phone;	//手机号
	private String credit;	//信用评级
	private String type;	//用户类型
	private String regDate;	//注册时间
	private double totalAssets;//总资产
	private double bondHoldings;//债券资产
	private double balance;	//账户余额
	private double creditNum;	//信用总额
	private double borTotal;//已借额度
	private double availableCredit;	//可用信用额
	/**
	 * @return the uid
	 */
	public int getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(int uid) {
		this.uid = uid;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the idNum
	 */
	public String getIdNum() {
		return idNum;
	}
	/**
	 * @param idNum the idNum to set
	 */
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the credit
	 */
	public String getCredit() {
		return credit;
	}
	/**
	 * @param credit the credit to set
	 */
	public void setCredit(String credit) {
		this.credit = credit;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return regDate;
	}
	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	/**
	 * @return the totalAssets
	 */
	public double getTotalAssets() {
		return totalAssets;
	}
	/**
	 * @param totalAssets the totalAssets to set
	 */
	public void setTotalAssets(double totalAssets) {
		this.totalAssets = totalAssets;
	}
	/**
	 * @return the bondHoldings
	 */
	public double getBondHoldings() {
		return bondHoldings;
	}
	/**
	 * @param bondHoldings the bondHoldings to set
	 */
	public void setBondHoldings(double bondHoldings) {
		this.bondHoldings = bondHoldings;
	}
	/**
	 * @return the balance
	 */
	public double getBalance() {
		return balance;
	}
	/**
	 * @param balance the balance to set
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}
	/**
	 * @return the creditNum
	 */
	public double getCreditNum() {
		return creditNum;
	}
	/**
	 * @param creditNum the creditNum to set
	 */
	public void setCreditNum(double creditNum) {
		this.creditNum = creditNum;
	}
	/**
	 * @return the borTotal
	 */
	public double getBorTotal() {
		return borTotal;
	}
	/**
	 * @param borTotal the borTotal to set
	 */
	public void setBorTotal(double borTotal) {
		this.borTotal = borTotal;
	}
	/**
	 * @return the availableCredit
	 */
	public double getAvailableCredit() {
		return availableCredit;
	}
	/**
	 * @param availableCredit the availableCredit to set
	 */
	public void setAvailableCredit(double availableCredit) {
		this.availableCredit = availableCredit;
	}
	
	
	
}
