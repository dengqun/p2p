/**
 * 
 */
package com.fzm.daointerface;

import java.util.List;

import com.fzm.entity.TokenUser;
import com.fzm.entity.User;
import com.fzm.entity.UserBackstage;


/**   
 *    
 * 项目名称：cgb_p2p   
 * 类名称：UserLoginDaoInterface   
 * 类描述：   
 * 创建人：maamin   
 * 创建时间：2017-8-9 下午4:38:23   
 * 修改人：maamin   
 * 修改时间：2017-8-9 下午4:38:23   
 * 修改备注：   
 * @version    
 *    
 */
public interface UserLoginDaoInterface {
	public User verificationUser(String username,String password);
	public void saveToken(int uid,String token);
	public TokenUser verificationToken(int uid);
	public boolean valiToken(int userId);
	public void updateTokenByUid(int uid,String token);
	public TokenUser getTokenUser(String uid);
	public boolean findUserById(String username,String password,String typeInt);
	public int register(String username,String password,String typeInt);
	public User getUser(String username);
	public void updateUserFlag(String name,String randomNumber);
}
