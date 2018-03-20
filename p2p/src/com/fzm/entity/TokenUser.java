/**
 * 
 */
package com.fzm.entity;

/**   
 *    
 * 项目名称：cgb_p2p   
 * 类名称：TokenUser   
 * 类描述：   
 * 创建人：maamin   
 * 创建时间：2017-8-2 下午4:19:29   
 * 修改人：maamin   
 * 修改时间：2017-8-2 下午4:19:29   
 * 修改备注：   
 * @version    
 *    
 */
public class TokenUser {
	private int id;
	private int uid;
	private String token;
	private String random;
	/**
	 * 
	 */
	public TokenUser() {
		super();
	}
	
	/**
	 * @param uid
	 * @param token
	 */
	public TokenUser(int uid, String token) {
		super();
		this.uid = uid;
		this.token = token;
	}

	/**
	 * @param uid
	 * @param token
	 * @param randomNum
	 */
	public TokenUser(int uid, String token, String random) {
		super();
		this.uid = uid;
		this.token = token;
		this.random = random;
	}

	/**
	 * @param id
	 * @param uid
	 * @param token
	 * @param randomNum
	 */
	public TokenUser(int id, int uid, String token, String random) {
		super();
		this.id = id;
		this.uid = uid;
		this.token = token;
		this.random = random;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TokenUser [id=" + id + ", uid=" + uid + ", token=" + token
				+ ", random=" + random + "]";
	}

	
	
}
