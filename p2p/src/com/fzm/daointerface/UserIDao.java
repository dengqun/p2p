package com.fzm.daointerface;

import java.util.List;
import java.util.Map;

import com.fzm.entity.User;

/**
 * FileName: userIDao.java
 * @Description :TODO
 * @author 创建人 王斌
 * @date 创建时间 2017-8-4下午6:43:55
 * @version v1.0
 *
 * Modification  History:
 * Date              Author           Version        
 * -------------------------------------------------
 * 2017-8-4           王斌                                          @version          
 *
 * Why & What is modified:
 */
public interface UserIDao {
	
	/**
	 * 返回用户信息
	 * */
	List<Map<String, Object>> getUserInfo(int uid);
	
	/**
	 * 查询用户信息
	 * */
	User gainUserInfo(int uid);
	
	/**
	 * 查询用户信息的实名认证情况
	 * */
	List<Map<String, Object>> getUser(int uid);
	
	/**
	 * 投资时修改用户的金钱
	 * */
	int updMoney(User vo);
	
	/**
	 * 修改用户的实名认证情况
	 * */
	int updUser(User vo);
	
	/**
	 * 修改用户的信用额度，信用等级
	 * */
	int updCredit(User vo);
	
	/**
	 * 获取phone是否存在
	 * */
	int intPhoneCount(String phone);
	
	/**
	 *  获取email是否存在
	 * */
	int intEmailCount(String email);
	
	/**
	 * 获取用户的实名认证信息（实名认证）
	 * */
	Map<String,Object> gainUserMsg1(int uid);
	
	/**
	 * 获取用户的实名认证信息（手机/邮箱更新）
	 * */
	List<Map<String,Object>> gainUserMsg2(int uid);
	
	User selAll(int uid);
	
	/**
	 * 修改所有已失效的借款用户的金额
	 * */
	int updBorrow(User vo);
}
