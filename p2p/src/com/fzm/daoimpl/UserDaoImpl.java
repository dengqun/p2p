package com.fzm.daoimpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fzm.daointerface.UserIDao;
import com.fzm.entity.User;

/**
 * FileName: userService.java
 * @Description :TODO
 * @author 创建人 王斌
 * @date 创建时间 2017-8-4下午7:06:40
 * @version v1.0
 *
 * Modification  History:
 * Date              Author           Version        
 * -------------------------------------------------
 * 2017-8-4           王斌                                          @version          
 *
 * Why & What is modified:
 */
@Component("user")
public class UserDaoImpl {


	@Autowired
	private UserIDao user;
	
	/**
	 * 实现了userIDao接口中的getUserInfo方法
	 * 返回用户信息
	 * */
	public List<Map<String, Object>> getUser(int uid){
		
		return user.getUserInfo(uid);
	}
	
	/**
	 * 实现了userIDao接口中的gainUserInfo方法
	 * 查询用户信息
	 * */
	public User findUserList(int uid){
		
		return user.gainUserInfo(uid);
	}
	
	/**
	 * 实现了userIDao接口中的updUser方法
	 * 修改用户的实名认证情况
	 * */
	public int updateUser(User vo){
		
		return user.updUser(vo);
	}
	
	/**
	 * 实现了userIDao接口中的getUser方法
	 * 查询用户信息的实名认证情况
	 * */
	public List<Map<String, Object>> gainUser(int uid){
		
		return user.getUser(uid);
	}
	
	/**
	 * 实现了userIDao接口中的updMonry方法
	 * 投资时修改用户的金钱
	 * */
	public int updateMoney(User vo){
		
		return user.updMoney(vo);
	}
	
	/**
	 *  实现了userIDao接口中的updCredit方法
	 * 	修改用户的信用等级和信用额度
	 * */
	public int updateCredit(User vo){
		
		return user.updCredit(vo);
	}
	
	/**
	 * 获取phone是否存在
	 * */
	public int intPhone(String phone){
		
		return user.intPhoneCount(phone);
	}
	
	/**
	 *  获取email是否存在
	 * */
	public int intEmail(String email){
		
		return user.intEmailCount(email);
	}
}
