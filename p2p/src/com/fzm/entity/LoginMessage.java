/**
 * 
 */
package com.fzm.entity;

/**   
 *    
 * 项目名称：cgb_p2p   
 * 类名称：Message   
 * 类描述：   封装登录后返回的信息
 * 创建人：maamin   
 * 创建时间：2017-8-1 下午2:13:39   
 * 修改人：maamin   
 * 修改时间：2017-8-1 下午2:13:39   
 * 修改备注：   
 * @version    
 *    
 */
public class LoginMessage {
	String msg;
	int uid;
	String token;
	String usertype;
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
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
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the usertype
	 */
	public String getUsertype() {
		return usertype;
	}
	/**
	 * @param usertype the usertype to set
	 */
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LoginMessage [msg=" + msg + ", uid=" + uid + ", token=" + token
				+ ", usertype=" + usertype + "]";
	}
	
	
	
}
