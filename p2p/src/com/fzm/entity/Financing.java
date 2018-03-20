package com.fzm.entity;

/**
 * 
 * @Description : 产品类
 * @author sdy
 * @date 2017-08-01
 * 
 */
public class Financing {
		private String pid;
		private double producet_money;//总金额
		private double producet_rate;		//利率
		private int producet_deadline;		//还款期限
		private  double producet_amount;	//投资金额
		private  int  producet_stateNo;			//产品状态
		private double producet_eRevenue;		//回笼资金
		public String getPid() {
			return pid;
		}
		public void setPid(String pid) {
			this.pid = pid;
		}
		public double getProducet_money() {
			return producet_money;
		}
		public void setProducet_money(double producet_money) {
			this.producet_money = producet_money;
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
		public double getProducet_amount() {
			return producet_amount;
		}
		public void setProducet_amount(double producet_amount) {
			this.producet_amount = producet_amount;
		}
		public int getProducet_stateNo() {
			return producet_stateNo;
		}
		public void setProducet_stateNo(int producet_stateNo) {
			this.producet_stateNo = producet_stateNo;
		}
		public double getProducet_eRevenue() {
			return producet_eRevenue;
		}
		public void setProducet_eRevenue(double producet_eRevenue) {
			this.producet_eRevenue = producet_eRevenue;
		}
		
		
	
		
		
}
