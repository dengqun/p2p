/**
 * 
 */
package com.fzm.service.serviceInterface;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.fzm.entity.TokenUser;
import com.fzm.service.serviceImpl.UserLoginServiceImpl;

/**   
 *    
 * 项目名称：cgb_p2p   
 * 类名称：UserLoginServiceInterface   
 * 类描述：   
 * 创建人：maamin   
 * 创建时间：2017-8-9 下午4:23:25   
 * 修改人：maamin   
 * 修改时间：2017-8-9 下午4:23:25   
 * 修改备注：   
 * @version    
 *    
 */
public interface UserLoginServiceInterface {
	public Map<String, Object> verificationUser(String username, String password);
	public boolean verificationToken(int userid,String token);
	public Map<String,Object> register(String username,String password,String type,UserLoginServiceImpl userLoginService);
	public TokenUser getTokenUser(String uid);
	public String ValiToken(HttpServletRequest req);
	public void updateTokenByUid(int userid, String token);
}
