package com.fzm.entity;

import java.util.Date;
/**
 * 
 * @Description : 状态类
 * @author sdy
 * @date 2017-08-01
 * 
 */
public class State {
			private int sid;
			private int stateNo;
			private int uid;
			private Date  stateDate;
			private int fid;
			private int borId;
			private String state;
			private String action;
			public String getAction() {
				return action;
			}
			public void setAction(String action) {
				this.action = action;
			}
			public String getState() {
				return state;
			}
			public void setState(String state) {
				this.state = state;
			}
			public int getSid() {
				return sid;
			}
			public void setSid(int sid) {
				this.sid = sid;
			}
			public int getStateNo() {
				return stateNo;
			}
			public void setStateNo(int stateNo) {
				this.stateNo = stateNo;
			}
			public int getUid() {
				return uid;
			}
			public void setUid(int uid) {
				this.uid = uid;
			}
		
			public Date getStateDate() {
				return stateDate;
			}
			public void setStateDate(Date stateDate) {
				this.stateDate = stateDate;
			}
			public int getFid() {
				return fid;
			}
			public void setFid(int fid) {
				this.fid = fid;
			}
			public int getBorId() {
				return borId;
			}
			public void setBorId(int borId) {
				this.borId = borId;
			}
}
