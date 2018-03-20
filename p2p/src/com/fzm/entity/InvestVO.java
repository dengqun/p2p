package com.fzm.entity;

/**
 * FileName: u_financingVO.java
 * @Description :TODO
 * @author 创建人 王斌
 * @date 创建时间 2017-8-3下午6:32:38
 * @version v1.0
 *
 * Modification  History:
 * Date              Author           Version        
 * -------------------------------------------------
 * 2017-8-3           王斌                                          @version          
 *
 * Why & What is modified:
 */
public class InvestVO {

	private int investid;//关联表id
	private int invest_uid;//用户id
	private String invest_producet_id;//借款包编号
	private double investMoney;//用户投资金额
	private String investDate;//用户的投资时间
	private double invest_eRevenue;//用户的预期收益
	private double invest_obtain;//回笼资金
	private double invest_scale;//投资占比
	private int invest_stateNo;//用户的投资状态
	private String hash;
	public int getInvestid() {
		return investid;
	}
	public void setInvestid(int investid) {
		this.investid = investid;
	}
	public int getInvest_uid() {
		return invest_uid;
	}
	public void setInvest_uid(int invest_uid) {
		this.invest_uid = invest_uid;
	}
	public String getInvest_product_id() {
		return invest_producet_id;
	}
	public void setInvest_producet_id(String invest_producet_id) {
		this.invest_producet_id = invest_producet_id;
	}
	public double getInvestMoney() {
		return investMoney;
	}
	public void setInvestMoney(double investMoney) {
		this.investMoney = investMoney;
	}
	public String getInvestDate() {
		return investDate;
	}
	public void setInvestDate(String investDate) {
		this.investDate = investDate;
	}
	public double getInvest_eRevenue() {
		return invest_eRevenue;
	}
	public void setInvest_eRevenue(double invest_eRevenue) {
		this.invest_eRevenue = invest_eRevenue;
	}
	public double getInvest_obtain() {
		return invest_obtain;
	}
	public void setInvest_obtain(double invest_obtain) {
		this.invest_obtain = invest_obtain;
	}
	public double getInvest_scale() {
		return invest_scale;
	}
	public void setInvest_scale(double invest_scale) {
		this.invest_scale = invest_scale;
	}
	public int getInvest_stateNo() {
		return invest_stateNo;
	}
	public void setInvest_stateNo(int invest_stateNo) {
		this.invest_stateNo = invest_stateNo;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	
	

}
