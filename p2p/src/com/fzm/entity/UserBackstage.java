/**
 * 
 */
package com.fzm.entity;

/**   
 *    
 * 项目名称：cgb_p2p   
 * 类名称：UserBackstage   
 * 类描述：后台用户信息表   
 * 创建人：maamin   
 * 创建时间：2017-8-2 上午11:58:11   
 * 修改人：maamin   
 * 修改时间：2017-8-2 上午11:58:11   
 * 修改备注：   
 * @version    
 *    
 */
public class UserBackstage {
	private int uid;	//用户id
	private String name;	//用户名称
	private int phone;	//手机号
	private String email;	//电子邮箱地址
	private String password;	//密码
	private String random;		//随机数
	private String public_key;	//公钥
	private String private_key;	//私钥
	
	/**
	 * 
	 */
	public UserBackstage() {
		super();
	}
	/**
	 * @param uid
	 * @param name
	 * @param phone
	 * @param email
	 * @param password
	 */
	public UserBackstage(int uid, String name, int phone, String email,
			String password) {
		super();
		this.uid = uid;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.password = password;
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
	 * @return the phone
	 */
	public int getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(int phone) {
		this.phone = phone;
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @return the random
	 */
	public String getRandom() {
		return random;
	}
	/**
	 * @param random the random to set
	 */
	public void setRandom(String random) {
		this.random = random;
	}
	/**
	 * @return the public_key
	 */
	public String getPublic_key() {
		return public_key;
	}
	/**
	 * @param public_key the public_key to set
	 */
	public void setPublic_key(String public_key) {
		this.public_key = public_key;
	}
	/**
	 * @return the private_key
	 */
	public String getPrivate_key() {
		return private_key;
	}
	/**
	 * @param private_key the private_key to set
	 */
	public void setPrivate_key(String private_key) {
		this.private_key = private_key;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserBackstage [uid=" + uid + ", name=" + name + ", phone="
				+ phone + ", email=" + email + ", password=" + password
				+ ", random=" + random + ", public_key=" + public_key
				+ ", private_key=" + private_key + "]";
	}
	
	
}
