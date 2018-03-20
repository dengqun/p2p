package com.fzm.entity;

/**
 * FileName: financingVO.java
 * @Description :对用户进入投资人主页面的数据表中的字段的映射   需要用到的表   financing u_financing
 * @author 创建人 王斌
 * @date 创建时间 2017-8-1下午4:58:36
 * @version v1.0
 *
 * Modification  History:
 * Date              Author           Version        
 * -------------------------------------------------
 * 2017-8-1           王斌                                          1.0          
 *
 * Why & What is modified:
 */
public class ProducetVO {

	private String pid;//借款包编号
	private String producet_name;//借款包名称
	private double producet_rate;//产品利率
	private int producet_deadline;//还款期限
	private double producet_money;//债券总份额
	private int producet_stateNo;//借款包状态
	private double producet_amount;//投资总份额
	private String producet_startDate;//起息日
	private  String producet_dateDue;//到息日
	private double producet_eRevenue;//回笼资金
	private double producet_raiseprocess;//投资进度
	private int investid;//关联表id
	private int invest_uid;//用户id
	private double investMoney;//用户投资金额
	private String investDate;//投资时间
	private double invest_eRevenue;//用户的预期收益
	private double invest_obtain;//回笼资金
	private double invest_scale;//投资占比
	private double producet_minMoney;//最小投资金额
	private int invest_stateNo;//用户的投资状态 
	private String hash;
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getProducet_name() {
		return producet_name;
	}
	public void setProducet_name(String producet_name) {
		this.producet_name = producet_name;
	}
	public double getProducet_rate() {
		return producet_rate;
	}
	public void setProducet_rate(double producet_rate) {
		this.producet_rate = producet_rate;
	}
	public int getProducet_deadline() {
		return producet_deadline;
	}
	public void setProducet_deadline(int producet_deadline) {
		this.producet_deadline = producet_deadline;
	}
	public double getProducet_money() {
		return producet_money;
	}
	public void setProducet_money(double producet_money) {
		this.producet_money = producet_money;
	}
	public int getProducet_stateNo() {
		return producet_stateNo;
	}
	public void setProducet_stateNo(int producet_stateNo) {
		this.producet_stateNo = producet_stateNo;
	}
	public double getProducet_amount() {
		return producet_amount;
	}
	public void setProducet_amount(double producet_amount) {
		this.producet_amount = producet_amount;
	}
	public String getProducet_startDate() {
		return producet_startDate;
	}
	public void setProducet_startDate(String producet_startDate) {
		this.producet_startDate = producet_startDate;
	}
	public String getProducet_dateDue() {
		return producet_dateDue;
	}
	public void setProducet_dateDue(String producet_dateDue) {
		this.producet_dateDue = producet_dateDue;
	}
	public double getProducet_eRevenue() {
		return producet_eRevenue;
	}
	public void setProducet_eRevenue(double producet_eRevenue) {
		this.producet_eRevenue = producet_eRevenue;
	}
	public double getProducet_raiseprocess() {
		return producet_raiseprocess;
	}
	public void setProducet_raiseprocess(double producet_raiseprocess) {
		this.producet_raiseprocess = producet_raiseprocess;
	}
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
	public double getProducet_minMoney() {
		return producet_minMoney;
	}
	public void setProducet_minMoney(double producet_minMoney) {
		this.producet_minMoney = producet_minMoney;
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
